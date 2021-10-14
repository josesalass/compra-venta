package com.queue.demo.controller;

import com.queue.demo.model.TelefonoUsuario; 
import com.queue.demo.service.TelefonoUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
