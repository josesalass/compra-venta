package com.queue.demo.service;

import java.sql.Timestamp;
import java.util.List; 
import com.queue.demo.model.Venta;


public interface VentaService {
	public List<Venta> buscarTodasLasVentas();
	public Venta buscarVentaPorId(int id); 
	public void borrarVentaPorId(int id);
	public void addVenta(Venta venta);
	void editarFecha(Timestamp fecha, int idVenta);
	void editarTipo(String tipo, int idVenta);
	void editarMetodoPago(String metodopago, int idVenta);
}
