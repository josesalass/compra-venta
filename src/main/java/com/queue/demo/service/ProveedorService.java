package com.queue.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.queue.demo.model.Proveedor;

public interface ProveedorService {
	public List <Proveedor> buscarPorNombre (String nombre);
	public List <Proveedor> buscarPorRut (String rut);
	public List <Proveedor> buscarTodosLosProveedores();
	void eliminarPorNombre(String nombre);
	void eliminarPorRut(String rut);
	public Proveedor guardarProveedor(Proveedor proveedor);
	
}
