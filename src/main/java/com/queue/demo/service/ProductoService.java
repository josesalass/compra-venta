package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.Producto;

public interface ProductoService {
	public List<Producto> buscarTodosLosProductos();
    public Producto buscarProductoPorId(String idproducto);
    public void guardarProducto(Producto producto);
    public void borrarProductoPorId(String idproducto);
}
