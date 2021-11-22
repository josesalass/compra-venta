package com.queue.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


	@PutMapping("/{idproducto}/aumentoDeStock/{cantidad}")  //aumenta stock de producto sin afectar flujo de caja
	public ResponseEntity<String> aumentoDeStock(@PathVariable int idproducto, @PathVariable int cantidad) {
		Producto producto = productoService.buscarProductoPorId(idproducto);
		int stockactual = producto.getStock();
		if (cantidad < 0) {
			return new ResponseEntity<>("La cantidad a aumentar es negativa " + cantidad +" y no puedes aumentar el stock del producto",HttpStatus.BAD_REQUEST);
		}
		int nuevacantidad = stockactual + cantidad;
		producto.setStock(nuevacantidad);
		Producto productoActualizado = productoService.actualizarProducto(idproducto, producto);
		return new ResponseEntity<>("Cantidad del producto: "+producto.getIdproducto() + " actualizada", HttpStatus.OK);
	}


	@PostMapping("/guardarProducto")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto saveProducto(@RequestBody Producto producto) {
	 	return productoService.guardarProducto(producto);
	 }



	 @PutMapping("/{idproducto}/reducirStock/{cantidad}")  // reduce stock de producto sin afectar flujo de caja
	 public ResponseEntity<String> reducirStock(@PathVariable int idproducto, @PathVariable int cantidad) {
	 	Producto producto = productoService.buscarProductoPorId(idproducto);
	 	int stockactual = producto.getStock();
	 	int nuevacantidad = stockactual - Math.abs(cantidad);
	 	if(nuevacantidad < 0) {
	 		System.out.println("La nueva cantidad es negativa " + nuevacantidad +" y no puedes reducir el stock del producto");
	 		return new ResponseEntity<>("La nueva cantidad es negativa " + nuevacantidad +" y no puedes reducir el stock del producto",HttpStatus.BAD_REQUEST);
	 	}
	 	producto.setStock(nuevacantidad);
	 	Producto productoActualizado = productoService.actualizarProducto(idproducto, producto);
	 	return new ResponseEntity<>("Cantidad del producto: "+producto.getIdproducto() + " actualizada", HttpStatus.OK);
	 }

}