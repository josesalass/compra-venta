package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.Compra;

public interface CompraService {
    public List<Compra> buscarTodasLasCompras();
    public Compra buscarCompraPorId(int idCompra);
    public void guardar(Compra compra);
    public void borrarCompraPorId(int idCompra);
}
