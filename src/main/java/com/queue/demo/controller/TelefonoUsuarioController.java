package com.queue.demo.controller;

import com.queue.demo.model.TelefonoUsuario; 
import com.queue.demo.service.TelefonoUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefonos_usuarios")
public class TelefonoUsuarioController {
	@Autowired
	TelefonoUsuarioService telefonoUsuarioService;
	
	@GetMapping("")
	public List<TelefonoUsuario> list(){
		return telefonoUsuarioService.buscarTodosLosTelefonosUsuarios();
	}

	@RequestMapping(method= RequestMethod.POST,value="/guardartelefono")
	public ResponseEntity<?> saveTelefono(@RequestBody TelefonoUsuario telefonoUsuario){
		if (telefonoUsuario !=null && telefonoUsuarioService.buscarTelefonoPorRut(telefonoUsuario.getRutusuario()) !=null){
			telefonoUsuarioService.guardar(telefonoUsuario);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

}
