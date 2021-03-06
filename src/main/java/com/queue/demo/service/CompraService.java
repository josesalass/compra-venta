package com.queue.demo.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.queue.demo.model.Compra;
import com.queue.demo.model.ViewRegistroComprasDetalle;
import com.queue.demo.model.ViewRegistroComprasResumen;

public interface CompraService {
    public List<Compra> buscarTodasLasCompras();
    public Optional<Compra> buscarCompraPorId(int idCompra);
    public boolean borrarCompraPorId(int idCompra);
    public Compra guardarCompra(Compra compra) throws Exception;
    public Compra actualizarCompra(int idcompra, Compra compra)throws Exception;
    public List<ViewRegistroComprasResumen> verRegistroCompraResumen();
    public List<ViewRegistroComprasDetalle> verRegistroCompraDetalle();
}
