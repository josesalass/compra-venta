package com.queue.demo.controller;


import java.util.List;
import java.util.Optional;

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
		Optional<Producto> producto = productoService.buscarProductoPorId(idproducto);
		int stockactual = producto.get().getStock();
		if (cantidad < 0) {
			return new ResponseEntity<>("La cantidad a aumentar es negativa " + cantidad +" y no puedes aumentar el stock del producto",HttpStatus.BAD_REQUEST);
		}
		int nuevacantidad = stockactual + cantidad;
		producto.get().setStock(nuevacantidad);
		Producto productoActualizado = productoService.actualizarProducto(idproducto, producto.get());
		return new ResponseEntity<>("Cantidad del producto: "+producto.get().getIdproducto() + " actualizada", HttpStatus.OK);
	}


	@PostMapping("/guardarProducto")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto saveProducto(@RequestBody Producto producto) throws Exception {
	 	return productoService.guardarProducto(producto);
	 }



	 @PutMapping("/{idproducto}/reducirStock/{cantidad}")  // reduce stock de producto sin afectar flujo de caja
	 public ResponseEntity<String> reducirStock(@PathVariable int idproducto, @PathVariable int cantidad) {
		 Optional<Producto> producto = productoService.buscarProductoPorId(idproducto);
	 	int stockactual = producto.get().getStock();
	 	int nuevacantidad = stockactual - Math.abs(cantidad);
	 	if(nuevacantidad < 0) {
	 		System.out.println("La nueva cantidad es negativa " + nuevacantidad +" y no puedes reducir el stock del producto");
	 		return new ResponseEntity<>("La nueva cantidad es negativa " + nuevacantidad +" y no puedes reducir el stock del producto",HttpStatus.BAD_REQUEST);
	 	}
	 	producto.get().setStock(nuevacantidad);
	 	Producto productoActualizado = productoService.actualizarProducto(idproducto, producto.get());
	 	return new ResponseEntity<>("Cantidad del producto: "+producto.get().getIdproducto() + " actualizada", HttpStatus.OK);
	 }


	@PutMapping("/{idproducto}/editarStockMin/{stockmin}") //Edita el valor de StockMin del producto
	public ResponseEntity<String> editarStockMin(@PathVariable int idproducto, @PathVariable int stockmin) {
		Optional<Producto> producto = productoService.buscarProductoPorId(idproducto);
		if (producto==null || stockmin <0){
			return new ResponseEntity<>("Debe ingresar un valor positivo al stockmin ",HttpStatus.BAD_REQUEST);
		}
		producto.get().setStockmin(stockmin);
		productoService.actualizarProducto(idproducto,producto.get());
		return new ResponseEntity<>("Cambio exitoso" ,HttpStatus.OK );

	}

	@PutMapping("/{idproducto}/editarValorCompra/{valorcompra}") //Edita el valor de Compra del producto
	public ResponseEntity<String> editarValorCompra(@PathVariable int idproducto, @PathVariable int valorcompra) {
		Optional<Producto> producto = productoService.buscarProductoPorId(idproducto);
		if (producto==null || valorcompra <0){
			return new ResponseEntity<>("Debe ingresar un valor positivo al valor de compra ",HttpStatus.BAD_REQUEST);
		}
		producto.get().setStockmin(valorcompra);
		productoService.actualizarProducto(idproducto,producto.get());
		return new ResponseEntity<>("Cambio exitoso" ,HttpStatus.OK );

	}

	@PutMapping("/{idproducto}/editarValorVenta/{valorventa}") //Edita el valor de Venta del producto
	public ResponseEntity<String> editarValorVenta(@PathVariable int idproducto, @PathVariable int valorventa) {
		Optional<Producto> producto = productoService.buscarProductoPorId(idproducto);
		if (producto==null || valorventa <0){
			return new ResponseEntity<>("Debe ingresar un valor positivo al valor de venta ",HttpStatus.BAD_REQUEST);
		}
		producto.get().setStockmin(valorventa);
		productoService.actualizarProducto(idproducto,producto.get());
		return new ResponseEntity<>("Cambio exitoso" ,HttpStatus.OK );

	}



}