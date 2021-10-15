package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.Proveedor;

public interface ProveedorService {
	public List <Proveedor> buscarTodosLosProveedores();
	public Proveedor buscarProveedorPorRut(String rut);
	public Proveedor buscarProveedorPorNombre(String nombre);
	public void guardar(Proveedor proveedor);
	public void borrarProveedorPorRut(String rut);
	public void borrarProveedorPorNombre(String nombre);
}
