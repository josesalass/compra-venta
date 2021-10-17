package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.Proveedor;

public interface ProveedorService {
	public List <Proveedor> buscarPorNombre (String nombre);
	public List <Proveedor> buscarPorRut (String rut);
	public List <Proveedor> buscarTodosLosProveedores();
	public void guardar(Proveedor proveedor);
	void eliminarPorNombre(String nombre);
	void eliminarPorRut(String rut);
	
}
