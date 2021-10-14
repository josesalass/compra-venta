package com.queue.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.queue.demo.model.Producto;
import com.queue.demo.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	 @Autowired
	    ProductoService productoservice;

	    @GetMapping("")
	    public List<Producto> list(){
	        return productoservice.buscarTodosLosProductos();
	    }
}