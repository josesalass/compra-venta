package com.queue.demo.controller;

import com.queue.demo.model.Usuario;
import com.queue.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("")
	public List <Usuario> list(){
		return usuarioService.buscarTodosLosUsuarios();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/guardarusuario")
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
		try {
			/*if (usuarioService.buscarUsuarioPorRut( usuario.getRutusuario() ) == null) {
				//El usuario no existe, por lo que se puede crear
				//return usuarioService.guardar(usuario);*/
				return new ResponseEntity<>(usuarioService.guardar(usuario), HttpStatus.CREATED);
/*
			}else {//El usuario existe así que no se puede crear
				System.err.println("El usuario ya existe"); //Debe comunicarse con front ends para informar
				return null;
			}*/
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
}
