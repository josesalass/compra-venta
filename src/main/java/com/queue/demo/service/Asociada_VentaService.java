package com.queue.demo.service;

import java.util.List;

import com.queue.demo.model.Asociada_Venta;

public interface Asociada_VentaService {
    public List<Asociada_Venta> buscarTodasLasVentasAsociadas();
    
	void saveAsociadaVenta(Asociada_Venta venta);
}
