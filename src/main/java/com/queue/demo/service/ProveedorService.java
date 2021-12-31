package com.queue.demo.service;

import java.util.List;
import java.util.Optional;

import com.queue.demo.model.Proveedor;

public interface ProveedorService {
	public Optional<Proveedor> buscarPorNombre (String nombre);
	public Optional<Proveedor> buscarPorRut (String rut);
	public List <Proveedor> buscarTodosLosProveedores();
	boolean eliminarPorNombre(String nombre);
	boolean eliminarPorRut(String rut);
	public Proveedor guardarProveedor(Proveedor proveedor, String rut) throws Exception;
	
}
