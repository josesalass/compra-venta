package com.queue.demo.controller;

import com.queue.demo.model.Venta;
import com.queue.demo.service.Asociada_VentaService;
import com.queue.demo.service.ClienteService;
import com.queue.demo.service.ProductoService;
import com.queue.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {
	@Autowired
	VentaService ventaService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	Asociada_VentaService aso_ventaService;
	
	@GetMapping("")
	public List<Venta> list(){
		return ventaService.buscarTodasLasVentas();
	}
	
	@PostMapping("/guardarVenta")
    public ResponseEntity<?> saveVenta(@RequestBody Venta venta) {
		try {
			return new ResponseEntity<>(ventaService.guardarVenta(venta), HttpStatus.CREATED);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
	
	@RequestMapping("/editFecha")
	public ResponseEntity<Void> editarFecha (@RequestParam(value="idventa",required=true) int idventa, @RequestParam(value="fecha",required=true) Timestamp fecha){
		boolean edited = ventaService.editarFecha(fecha, idventa);
		if (edited){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/editTipo")
	public ResponseEntity<Void> editarTipo (@RequestParam(value="idventa",required=true) int idventa, @RequestParam(value="tipoventa",required=true) String tipoventa){
		boolean edited = ventaService.editarTipo(tipoventa,idventa);
		if (edited){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/editMetodoPago")
	public ResponseEntity<Void> editarMetodoPago (@RequestParam(value="idventa",required=true) int idventa, @RequestParam(value="metodopago",required=true) String metodopago){
		boolean edited = ventaService.editarMetodoPago(metodopago,idventa);
		if (edited){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
