package com.queue.demo.service;

import java.util.List;
import java.util.Optional;

import com.queue.demo.model.Producto;

public interface ProductoService {
	public List<Producto> buscarTodosLosProductos();
    public Optional<Producto> buscarProductoPorId(int idproducto);
    public Producto guardarProducto(Producto producto) throws Exception;
    public void borrarProductoPorId(int idproducto);
    public Producto actualizarProducto(int idproducto, Producto producto);
}
