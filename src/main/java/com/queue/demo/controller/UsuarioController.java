package com.queue.demo.controller;

import com.queue.demo.model.Usuario;
import com.queue.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
