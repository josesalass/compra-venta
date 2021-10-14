package com.queue.demo.service;

import java.util.List; 
import com.queue.demo.model.Venta;


public interface VentaService {
	public List<Venta> buscarTodasLasVentas();
	public Venta buscarVentaPorId(int id);
	public void guardar(Venta venta);
	public void borrarVentaPorId(int id);
	
}
