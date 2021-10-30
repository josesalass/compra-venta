package com.queue.demo.service;

import com.queue.demo.model.Asociada_Venta;
import com.queue.demo.model.Compra;
import com.queue.demo.model.PerteneceACompra;
import com.queue.demo.model.Producto;
import com.queue.demo.model.Venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
	public Compra guardarCompra(Compra compra) {
		Compra nuevaCompra = new Compra();
		nuevaCompra.setFecha(compra.getFecha());
		nuevaCompra.setRutempresa(compra.getRutempresa());
		nuevaCompra.setRutusuario(compra.getRutusuario());
		nuevaCompra.getCompraproductos().addAll((compra.getCompraproductos()
				.stream()
				.map (PerteneceACompra -> {
					Producto producto = productoService.buscarProductoPorId(PerteneceACompra.getProducto().getIdproducto());
					PerteneceACompra perteneceACompra = new PerteneceACompra();
					perteneceACompra.setProducto(producto);
					perteneceACompra.setCompra(nuevaCompra);
					perteneceACompra.setCantidad(PerteneceACompra.getCantidad());
					producto.setStock(producto.getStock()+perteneceACompra.getCantidad());
					return perteneceACompra;
				}).collect(Collectors.toList())));
		return repCompra.save(nuevaCompra);
	}
}
