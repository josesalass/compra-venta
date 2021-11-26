package com.queue.demo.service;

import com.queue.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
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
	
	@Override
	public List<Venta> buscarTodasLasVentas(){
		return repVenta.findAll();
	}
	
	@Override
	public Venta buscarVentaPorId(int id) {
		return repVenta.findById(id).get();
	} 
	
	@Override
	public void borrarVentaPorId(int id) {
		repVenta.deleteById(id);
	}
	
	@Override
	public Venta guardarVenta(Venta venta) throws Exception{
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
		return repVenta.save(nuevaVenta);

	}
	@Override
	public boolean editarFecha(Timestamp fecha, int idVenta) {
		try{
			repVenta.editarFecha(idVenta, fecha);
			return true;
		}catch(NullPointerException e) {
			return false;
		}
	}
	
	@Override
	public boolean editarTipo(String tipoventa, int idVenta) {
		try{
			repVenta.editarTipo(idVenta, tipoventa);
			return true;
		}catch(NullPointerException e) {
			return false;
		}
	}
	
	@Override
	public boolean editarMetodoPago(String metodopago, int idVenta) {
		try{
			repVenta.editarMetodoPago(idVenta, metodopago);
			return true;
		}catch(NullPointerException e) {
			return false;
		}
	}

	@Override
	public Venta actualizarVenta(int idventa, Venta venta) {
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
