package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.Factura;

public interface FacturaService {
    public List<Factura> buscarTodasLasFacturas();
    public Factura buscarFacturaPorId(int idVenta);
    public void guardar(Factura factura);
    public void borrarFacturaPorId(int idVenta);
}