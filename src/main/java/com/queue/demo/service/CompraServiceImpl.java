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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.queue.demo.repository.*;

@Service
@Transactional
public class CompraServiceImpl implements CompraService{

    @Autowired
    RepositorioCompra repCompra;
    
    @Autowired
    ProductoService productoService;

	@Autowired
	UsuarioService usuarioService;

	@PersistenceContext
	private EntityManager em;

    @Override
    public List<Compra> buscarTodasLasCompras() {
        return repCompra.findAll();
    }

    @Override
    public Optional<Compra> buscarCompraPorId(int idCompra) {
		Optional<Compra> optional = repCompra.findById(idCompra);
		if (optional.isPresent()){
			return Optional.of(optional.get());
		}
		return Optional.empty();
    }

    @Override
    public boolean borrarCompraPorId(int idCompra) {
		try{
			Compra compra = repCompra.getById(idCompra);
			repCompra.delete(compra);
			return true;
		}catch (EntityNotFoundException e){
			return false;
		}
    }

	@Override
	public Compra guardarCompra(Compra compra) throws Exception {
			Optional<Usuario> usuario = usuarioService.buscarUsuarioPorRut(compra.getRutusuario());
			if(usuario.isEmpty()){
				throw new Exception("El usuario asignado a la venta no existe.");
			}
			if (usuario.get().getRolusuario()!=Usuario.ADMIN_COMPRAS || usuario.get().getRolusuario()!=Usuario.ADMIN){
				throw new AuthException("Usuario no autorizado para cometer la acción.");
			}

			if(compra.getFecha()==null|| compra.getRutempresa()==null|| compra.getRutusuario()==null|| compra.getCompraproductos()==null){
				throw new Exception();
			}

			Compra nuevaCompra = new Compra();
			nuevaCompra.setFecha(compra.getFecha());
			nuevaCompra.setRutempresa(compra.getRutempresa());
			nuevaCompra.setRutusuario(compra.getRutusuario());
			nuevaCompra.getCompraproductos().addAll((compra.getCompraproductos()
					.stream()
					.map(PerteneceACompra -> {
						Optional<Producto> producto = productoService.buscarProductoPorId(PerteneceACompra.getProducto().getIdproducto());
						PerteneceACompra perteneceACompra = new PerteneceACompra();
						perteneceACompra.setProducto(producto.get());
						perteneceACompra.setCompra(nuevaCompra);
						perteneceACompra.setCantidad(PerteneceACompra.getCantidad());
						producto.get().setStock(producto.get().getStock() + perteneceACompra.getCantidad());
						return perteneceACompra;
					}).collect(Collectors.toList())));
			return repCompra.save(nuevaCompra);

	}

	@Override
	public Compra actualizarCompra (int idcompra, Compra compra) {
		repCompra.save(compra);
		return compra;
	}

	@Override
	public List<ViewRegistroComprasResumen> verRegistroCompraResumen(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroComprasResumen.class);
		cq.select(root);

		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<ViewRegistroComprasDetalle> verRegistroCompraDetalle(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root root = cq.from(ViewRegistroComprasDetalle.class);
		cq.select(root);

		return em.createQuery(cq).getResultList();
	}
}
