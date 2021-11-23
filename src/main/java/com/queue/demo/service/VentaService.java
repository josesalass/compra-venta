package com.queue.demo.service;

import java.sql.Timestamp;
import java.util.List; 
import com.queue.demo.model.Venta;


public interface VentaService {
	public List<Venta> buscarTodasLasVentas();
	public Venta buscarVentaPorId(int id); 
	public void borrarVentaPorId(int id);
	public Venta guardarVenta(Venta venta) throws Exception;
	boolean editarFecha(Timestamp fecha, int idVenta);
	boolean editarTipo(String tipo, int idVenta);
	boolean editarMetodoPago(String metodopago, int idVenta);

	public Venta actualizarVenta(int idventa, Venta venta);
}
