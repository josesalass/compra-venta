package com.queue.demo.service;

import com.queue.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import com.queue.demo.repository.*;

@Service
@Transactional
public class VentaServiceImpl implements VentaService{

	@PersistenceContext
	private EntityManager em;

	@Autowired
	RepositorioVenta repVenta;
	
	@Autowired
	ProductoService productoService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ClienteService clienteService;
	
	@Override
	public List<Venta> buscarTodasLasVentas(){
		return repVenta.findAll();
	}
	
	@Override
	public Optional<Venta> buscarVentaPorId(int id) {
		Optional<Venta> optional=repVenta.findById(id);
		if(optional.isPresent()){
			return Optional.of(optional.get());
		}
		return Optional.empty();
	} 
	
	@Override
	public boolean borrarVentaPorId(int id) {
		try {
			Venta venta = repVenta.getById(id);
			repVenta.deleteById(id);
			return true;
		}catch (EntityNotFoundException e){
			return false;
		}
	}
	
	@Override
	public Venta guardarVenta(Venta venta) throws Exception{
		Optional<Usuario> usuario = usuarioService.buscarUsuarioPorRut(venta.getRutusuario());
		Optional<Cliente> cliente = clienteService.buscarClientePorRut(venta.getRutcliente());
		if(usuario.isEmpty()){
			throw new Exception("El usuario asignado a la venta no existe.");
		}
		if (cliente.isEmpty() && venta.getTipoventa().equals("factura")){
			throw new Exception("El cliente asignado a la factura no está registrado.");
		}
		if (usuario.get().getRolusuario()!=Usuario.ADMIN_VENTAS && usuario.get().getRolusuario()!=Usuario.ADMIN){
			throw new AuthException("Usuario no autorizado para cometer la acción.");
		}
		/*Iterator<Asociada_Venta> a = venta.getVentaproductos().iterator();
		while(a.hasNext()){
			Asociada_Venta as = a.next();
			if ( as.getProducto().getStock() < as.getCantidad()){
				throw new Exception("No se puede realizar la venta debido a que un producto no posee el suficiente stock documentado.");
			}
		} */
		Venta nuevaVenta = new Venta();
		if (venta.getFecha() == null || venta.getMetodopago() == null || venta.getRutusuario() == null || venta.getTipoventa() == null){
			throw new Exception();
		}
		nuevaVenta.setFecha(venta.getFecha());
		nuevaVenta.setTipoventa(venta.getTipoventa());
		nuevaVenta.setMetodopago(venta.getMetodopago());
		nuevaVenta.setRutusuario(venta.getRutusuario());
		nuevaVenta.setRutcliente(venta.getRutcliente());
		nuevaVenta.getVentaproductos().addAll((venta.getVentaproductos()
				.stream()
				.map(Asociada_Venta -> {
					Optional<Producto> producto = productoService.buscarProductoPorId(Asociada_Venta.getProducto().getIdproducto());
					Asociada_Venta asociadaVenta = new Asociada_Venta();
					asociadaVenta.setProducto(producto.get());
					asociadaVenta.setVenta(nuevaVenta);
					asociadaVenta.setCantidad(Asociada_Venta.getCantidad());
					producto.get().setStock(producto.get().getStock()-asociadaVenta.getCantidad());
					return asociadaVenta;
				}).collect(Collectors.toList())));

		nuevaVenta.setCliente(cliente.get());
		return repVenta.save(nuevaVenta);

	}

	@Override
	public Venta actualizarVenta(int idventa, Venta venta) throws Exception {
		Optional<Usuario> usuario = usuarioService.buscarUsuarioPorRut(venta.getRutusuario());
		if(usuario.isEmpty()){
			throw new Exception("El usuario asignado a la venta no existe.");
		}
		if (usuario.get().getRolusuario()!=Usuario.ADMIN_VENTAS && usuario.get().getRolusuario()!=Usuario.ADMIN){
			throw new AuthException("Usuario no autorizado para cometer la acción.");
		}
		repVenta.save(venta);
		return venta;
	}

	@Override
	public List<ViewRegistroVentasResumen> verRegistroVentaResumen(String tipoventa){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroVentasResumen.class);
		cq.select(root).where(cb.equal(root.get("tipoventa"), tipoventa));
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<ViewRegistroVentasResumen> verRegistroVentaResumen(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroVentasResumen.class);
		cq.select(root);
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<ViewRegistroVentasDetalle> verRegistroVentaDetalle(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroVentasDetalle.class);
		cq.select(root);
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<ViewRegistroVentasDetalle> verRegistroVentaDetalle(String tipoventa){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroVentasDetalle.class);
		cq.select(root).where(cb.equal(root.get("tipoventa"), tipoventa));
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<ViewRegistroVentasResumen> verRegistroVentaResumenDia(Timestamp dia){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroVentasResumen.class);
		cq.select(root).where(cb.equal(root.get("fecha"), dia));
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<ViewRegistroVentasResumen> verRegistroVentaResumenEntreDias(Timestamp dia1,Timestamp dia2){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroVentasResumen.class);
		cq.select(root).where(cb.between(root.get("fecha"),dia1,dia2));
		return em.createQuery(cq).getResultList();
	}
	@Override
	public List<ViewRegistroVentasDetalle> verRegistroVentaDetalleDia(Timestamp dia){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroVentasDetalle.class);
		cq.select(root).where(cb.equal(root.get("fecha"), dia));
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<ViewRegistroVentasDetalle> verRegistroVentaDetalleEntreDias(Timestamp dia1,Timestamp dia2){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroVentasDetalle.class);
		cq.select(root).where(cb.between(root.get("fecha"),dia1,dia2));
		return em.createQuery(cq).getResultList();
	}
}
