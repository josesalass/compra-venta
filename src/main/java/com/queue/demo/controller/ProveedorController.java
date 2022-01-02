package com.queue.demo.controller;

import com.queue.demo.model.Proveedor;
import com.queue.demo.service.AuthException;
import com.queue.demo.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
	@RequestMapping("/proveedores")

public class ProveedorController {
	@Autowired
	ProveedorService proveedorService;
	
	@GetMapping("")
	public ResponseEntity<List<Proveedor>> list(){
		List<Proveedor> proveedores = proveedorService.buscarTodosLosProveedores();
		if(!proveedores.isEmpty()){
			return new ResponseEntity<>(proveedores, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/findNombre")
	public ResponseEntity<Optional> buscarPorNombre (@RequestParam(value="nombre",required=true) String nombre){
		Optional<Proveedor> resultado = proveedorService.buscarPorNombre(nombre);
		if(resultado.isPresent()){
			return new ResponseEntity<>(resultado,HttpStatus.OK);
		}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	
	@GetMapping("/findRut")
	public ResponseEntity<Optional> buscarPorRut (@RequestParam(value="rut",required=true) String rut){
		Optional<Proveedor> resultado = proveedorService.buscarPorRut(rut);
		if(resultado.isPresent()){
			return new ResponseEntity<>(resultado,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping("/deleteNombre")
	public ResponseEntity<HttpStatus> eliminarPorNombre (@RequestParam(value="nombre",required=true) String nombre){
		boolean response= proveedorService.eliminarPorNombre(nombre);
		if(response){
			return new ResponseEntity(HttpStatus.OK);
		}else{
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping("/deleteRut")
	public ResponseEntity<HttpStatus> eliminarPorRut (@RequestParam(value="rut",required=true) String rut){
		boolean response= proveedorService.eliminarPorRut(rut);
		if(response){
			return new ResponseEntity(HttpStatus.OK);
		}else{
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/guardarProveedor")
	public ResponseEntity<?> saveProveedor(@RequestParam(value="rut",required=true) String rut , @RequestBody Proveedor proveedor) {
		try {
			return new ResponseEntity<>(proveedorService.guardarProveedor(proveedor,rut), HttpStatus.CREATED);
		}catch(AuthException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


}
		
