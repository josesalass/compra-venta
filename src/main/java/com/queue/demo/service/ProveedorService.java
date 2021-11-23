package com.queue.demo.service;

import java.util.List;

import com.queue.demo.model.Proveedor;

public interface ProveedorService {
	public Proveedor buscarPorNombre (String nombre);
	public Proveedor buscarPorRut (String rut);
	public List <Proveedor> buscarTodosLosProveedores();
	boolean eliminarPorNombre(String nombre);
	boolean eliminarPorRut(String rut);
	public Proveedor guardarProveedor(Proveedor proveedor) throws Exception;
	
}
