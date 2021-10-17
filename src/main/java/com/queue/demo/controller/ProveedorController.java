package com.queue.demo.controller;

import com.queue.demo.model.Proveedor;
import com.queue.demo.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/proveedores")

public class ProveedorController {
	@Autowired
	ProveedorService proveedorService;
	
	@GetMapping("")
	public List<Proveedor> list(){
		return proveedorService.buscarTodosLosProveedores();
	}
	
	@GetMapping("/findNombre")
	public List<Proveedor> buscarPorNombre (@RequestParam(value="nombre",required=true) String nombre){
		return proveedorService.buscarPorNombre(nombre);
	}
	
	@GetMapping("/findRut")
	public List<Proveedor> buscarPorRut (@RequestParam(value="rut",required=true) String rut){
		return proveedorService.buscarPorRut(rut);
	}
}
	
	
		
