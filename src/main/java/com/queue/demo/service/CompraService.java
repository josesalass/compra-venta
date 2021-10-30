package com.queue.demo.service;

import java.sql.Timestamp;
import java.util.List;
import com.queue.demo.model.Compra;
import com.queue.demo.model.Venta;

public interface CompraService {
    public List<Compra> buscarTodasLasCompras();
    public Compra buscarCompraPorId(int idCompra);
    public void guardar(Compra compra);
    public void borrarCompraPorId(int idCompra);
    public Compra guardarCompra(Compra compra);
	void editarRutEmpresa(String rutempresa, int idcompra);
	void editarFecha(Timestamp fecha, int idcompra);
}
