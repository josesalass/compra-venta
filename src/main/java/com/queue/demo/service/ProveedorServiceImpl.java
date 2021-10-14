package com.queue.demo.service;

import com.queue.demo.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService{
	
	@Autowired
	RepositorioProveedor repProveedor;
	
	@Override
	public List<Proveedor> buscarTodosLosProveedores(){
		return repProveedor.findAll();
	}
	@Override
	public Proveedor buscarProveedorPorRut(String rut) {
		return repProveedor.findById(1).get();
	}
	
	@Override
	public Proveedor buscarProveedorPorNombre(String nombre) {
		return repProveedor.findById(1).get();
	}
	@Override
	public void guardar(Proveedor proveedor) {
		repProveedor.save(proveedor);
	}
	@Override
	public void borrarProveedorPorRut(String rut) {
		repProveedor.deleteById(1);
	}
	@Override
	public void borrarProveedorPorNombre(String nombre) {
		
	}
}
