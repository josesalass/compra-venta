package com.queue.demo.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.queue.demo.model.Venta;
import com.queue.demo.model.ViewRegistroVentasDetalle;
import com.queue.demo.model.ViewRegistroVentasResumen;


public interface VentaService {
	public List<Venta> buscarTodasLasVentas();
	public Optional<Venta> buscarVentaPorId(int id);
	public boolean borrarVentaPorId(int id);
	public Venta guardarVenta(Venta venta) throws Exception;
/*
	boolean editarFecha(Timestamp fecha, int idVenta);
	boolean editarTipo(String tipo, int idVenta);
	boolean editarMetodoPago(String metodopago, int idVenta);
*/
	public Venta actualizarVenta(int idventa, Venta venta);
	List<ViewRegistroVentasResumen> verRegistroVentaResumen(String tipo);

	List<ViewRegistroVentasResumen> verRegistroVentaResumen();

	List<ViewRegistroVentasDetalle> verRegistroVentaDetalle();

	List<ViewRegistroVentasDetalle> verRegistroVentaDetalle(String tipoventa);

	List<ViewRegistroVentasResumen> verRegistroVentaResumenDia(Timestamp dia);

	List<ViewRegistroVentasResumen> verRegistroVentaResumenEntreDias(Timestamp dia1,Timestamp dia2);

	List<ViewRegistroVentasDetalle> verRegistroVentaDetalleDia(Timestamp dia);

	List<ViewRegistroVentasDetalle> verRegistroVentaDetalleEntreDias(Timestamp dia1,Timestamp dia2);
}
