package com.queue.demo.controller;

import com.queue.demo.model.RepresentanteProveedor;
import com.queue.demo.model.TelefonoRepresentante;
import com.queue.demo.service.RepresentanteProveedorService;
import com.queue.demo.service.TelefonoRepresentanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefonorepresentante")
public class TelefonoRepresentanteController {
	@Autowired
	TelefonoRepresentanteService telefonoRepService;
	@Autowired
	RepresentanteProveedorService representanteProveedorService;
	
	@GetMapping("")
	public List<TelefonoRepresentante> list(){
		return telefonoRepService.buscarTodosLosTelefonos();
	}

	@RequestMapping(method= RequestMethod.POST,value="/guardartelefono")
	public ResponseEntity<?> saveTelefono(@RequestBody TelefonoRepresentante telefonoRepresentante) throws Exception {
		if (telefonoRepresentante !=null && representanteProveedorService.buscarRepresentantePorRut(telefonoRepresentante.getRutrep()) !=null){
			telefonoRepService.guardar(telefonoRepresentante);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
