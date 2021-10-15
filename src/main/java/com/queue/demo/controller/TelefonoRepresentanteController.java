package com.queue.demo.controller;

import com.queue.demo.model.TelefonoRepresentante;
import com.queue.demo.service.TelefonoRepresentanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/telefonorepresentante")
public class TelefonoRepresentanteController {
	@Autowired
	TelefonoRepresentanteService telefonoRepService;
	
	@GetMapping("")
	public List<TelefonoRepresentante> list(){
		return telefonoRepService.buscarTodosLosTelefonos();
	}
}
