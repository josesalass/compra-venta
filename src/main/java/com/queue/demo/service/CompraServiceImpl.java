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

import java.sql.Timestamp;
import java.util.List;
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
    ProveedorService provService;

	@PersistenceContext
	private EntityManager em;

    @Override
    public List<Compra> buscarTodasLasCompras() {
        return repCompra.findAll();
    }

    @Override
    public Compra buscarCompraPorId(int idCompra) {
        return repCompra.findById(1).get();
    }

    @Override
    public void guardar(Compra compra) {
        repCompra.save(compra);
    }

    @Override
    public void borrarCompraPorId(int idCompra) {
        repCompra.deleteById(1);
    }

	@Override
	public Compra guardarCompra(Compra compra) throws Exception {

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
						Producto producto = productoService.buscarProductoPorId(PerteneceACompra.getProducto().getIdproducto());
						PerteneceACompra perteneceACompra = new PerteneceACompra();
						perteneceACompra.setProducto(producto);
						perteneceACompra.setCompra(nuevaCompra);
						perteneceACompra.setCantidad(PerteneceACompra.getCantidad());
						producto.setStock(producto.getStock() + perteneceACompra.getCantidad());
						return perteneceACompra;
					}).collect(Collectors.toList())));
			return repCompra.save(nuevaCompra);

	}
	
	@Override
  	public void editarFecha(Timestamp fecha, int idcompra) {
		try{
  			repCompra.editarFecha(idcompra, fecha);

  		}catch(NullPointerException e) {
  		}
  	}
  	
  	@Override
  	public void editarRutEmpresa(String rutempresa, int idcompra) {
  		try{
  				repCompra.editarRutEmpresa(idcompra, rutempresa);
  		}catch(NullPointerException e) {
  			
  		}
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
}
