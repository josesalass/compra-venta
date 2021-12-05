package com.queue.demo.controller;

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
	public ResponseEntity<List<TelefonoRepresentante>> list(){
		List<TelefonoRepresentante> telefonos = telefonoRepService.buscarTodosLosTelefonos();
		if(!telefonos.isEmpty()){
			return new ResponseEntity<>(telefonos, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method= RequestMethod.POST,value="/guardartelefono")
	public ResponseEntity<?> saveTelefono(@RequestBody TelefonoRepresentante telefonoRepresentante) throws Exception {
		if (telefonoRepresentante !=null && representanteProveedorService.buscarRepresentantePorRut(telefonoRepresentante.getRutrep()) !=null){
			TelefonoRepresentante a =telefonoRepService.guardar(telefonoRepresentante);
			return new ResponseEntity<>(a,HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
