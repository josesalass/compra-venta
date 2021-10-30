package com.queue.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.queue.demo.model.Producto;
import com.queue.demo.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	 @Autowired
	    ProductoService productoService;

	    @GetMapping("")
	    public List<Producto> list(){
	        return productoService.buscarTodosLosProductos();
	    }
	    
	    @RequestMapping("/editstock")
	    public void editarStock(@RequestParam (value="idproducto",required=true )int idProducto,@RequestParam (value="cantidad",required=true ) int cantidad,@RequestParam (value="tipo",required=true ) int tipo){
	    	if(tipo==0) {
	    		productoService.editarStock(idProducto, cantidad);
	    	}
	    	else {
	    		//aqui se implementara posteriormente el flujo de caja por lo que este aumentaria
	    	}
	    }
	    
	    @PostMapping("/guardarProducto")
	    public void saveProducto(@RequestBody Producto producto) {
	        productoService.guardarProducto(producto);
	    }
	    
	    @PutMapping("/{idproducto}/reducirStock/{cantidad}")
	    public Producto reducirStock(@PathVariable int idproducto, @PathVariable int cantidad) {
	    	Producto producto = productoService.buscarProductoPorId(idproducto);
	    	int stockactual = producto.getStock();
	    	int nuevacantidad = stockactual - cantidad;
	    	if(nuevacantidad < 1) {
	    		System.out.println("La nueva cantidad es negativa " + nuevacantidad +" y no puedes reducir el stock del producto");
	    		return producto;
	    	}
	    	producto.setStock(nuevacantidad);  
			return productoService.actualizarProducto(idproducto, producto);
		}

}