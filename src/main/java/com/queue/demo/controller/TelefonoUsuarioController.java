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
	public ResponseEntity<List<TelefonoUsuario>> list(){
		List<TelefonoUsuario> tfs = telefonoUsuarioService.buscarTodosLosTelefonosUsuarios();
		if(!tfs.isEmpty()){
			return new ResponseEntity<>(tfs, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method= RequestMethod.POST,value="/guardartelefono")
	public ResponseEntity<?> saveTelefono(@RequestBody TelefonoUsuario telefonoUsuario) throws Exception{
		if (telefonoUsuario !=null && telefonoUsuarioService.buscarTelefonoPorRut(telefonoUsuario.getRutusuario()) !=null){
			TelefonoUsuario a= telefonoUsuarioService.guardar(telefonoUsuario);
			return new ResponseEntity<>(a,HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
