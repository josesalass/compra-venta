package com.queue.demo.controller;

import com.queue.demo.model.Venta;
import com.queue.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
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
	
	@RequestMapping(method=RequestMethod.POST, value="/guardarventa")
	public void addVenta(@RequestBody Venta venta) {
		ventaService.addVenta(venta);
		
	}
	
	@RequestMapping("/editFecha")
	public void editarFecha (@RequestParam(value="idventa",required=true) int idventa, @RequestParam(value="fecha",required=true) Timestamp fecha){
		ventaService.editarFecha(fecha, idventa);
	}
	
	@RequestMapping("/editTipo")
	public void editarTipo (@RequestParam(value="idventa",required=true) int idventa, @RequestParam(value="tipoventa",required=true) String tipoventa){
		ventaService.editarTipo(tipoventa,idventa);
	}
	
	@RequestMapping("/editMetodoPago")
	public void editarMetodoPago (@RequestParam(value="idventa",required=true) int idventa, @RequestParam(value="metodopago",required=true) String metodopago){
		ventaService.editarMetodoPago(metodopago,idventa);
	}
}
