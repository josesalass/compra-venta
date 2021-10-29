package com.queue.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	    
	    @RequestMapping("/editstock")
	    public void editarStock(@RequestParam (value="idproducto",required=true )int idProducto,@RequestParam (value="cantidad",required=true ) int cantidad,@RequestParam (value="tipo",required=true ) int tipo){
	    	if(tipo==0) {
	    		productoservice.editarStock(idProducto, cantidad);
	    	}
	    	else {
	    		//aqui se implementara posteriormente el flujo de caja por lo que este aumentaria
	    	}
	    }
}