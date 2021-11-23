package com.queue.demo.service;

import com.queue.demo.model.Asociada_Venta;
import com.queue.demo.model.Producto;
import com.queue.demo.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import com.queue.demo.repository.*;

@Service
@Transactional
public class VentaServiceImpl implements VentaService{
	
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
					Producto producto = productoService.buscarProductoPorId(Asociada_Venta.getProducto().getIdproducto());
					Asociada_Venta asociadaVenta = new Asociada_Venta();
					asociadaVenta.setProducto(producto);
					asociadaVenta.setVenta(nuevaVenta);
					asociadaVenta.setCantidad(Asociada_Venta.getCantidad());
					producto.setStock(producto.getStock()-asociadaVenta.getCantidad());
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
}
