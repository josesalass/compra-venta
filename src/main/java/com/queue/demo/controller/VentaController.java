package com.queue.demo.controller;

import com.queue.demo.model.Venta;
import com.queue.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {
	@Autowired
	VentaService ventaService;
	
	@GetMapping("")
	public List<Venta> list(){
		return ventaService.buscarTodasLasVentas();
	}
}
