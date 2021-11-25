package com.queue.demo.controller;

import com.queue.demo.model.Venta;
import com.queue.demo.model.ViewRegistroVentasResumen;
import com.queue.demo.service.Asociada_VentaService;
import com.queue.demo.service.ClienteService;
import com.queue.demo.service.ProductoService;
import com.queue.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
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
	public ResponseEntity<List<Venta>> list() {
		List<Venta> ventas = ventaService.buscarTodasLasVentas();
		if (!ventas.isEmpty()){
			return new ResponseEntity<>(ventas,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/guardarVenta")
	public ResponseEntity<Venta> saveVenta(@RequestBody Venta venta) {
		try {
			return new ResponseEntity<>(ventaService.guardarVenta(venta),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	/*
	@RequestMapping("/editFecha")
	public ResponseEntity<Void> editarFecha(@RequestParam(value = "idventa", required = true) int idventa, @RequestParam(value = "fecha", required = true) Timestamp fecha) {
		boolean edited = ventaService.editarFecha(fecha, idventa);
		if (edited) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping("/editTipo")
	public ResponseEntity<Void> editarTipo(@RequestParam(value = "idventa", required = true) int idventa, @RequestParam(value = "tipoventa", required = true) String tipoventa) {
		boolean edited = ventaService.editarTipo(tipoventa, idventa);
		if (edited) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping("/editMetodoPago")
	public ResponseEntity<Void> editarMetodoPago(@RequestParam(value = "idventa", required = true) int idventa, @RequestParam(value = "metodopago", required = true) String metodopago) {
		boolean edited = ventaService.editarMetodoPago(metodopago, idventa);
		if (edited) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	*/

	@PutMapping("/{idventa}/cambiarFecha/{fecha}")
	public ResponseEntity<String> cambioFechaPrueba(@PathVariable int idventa, @PathVariable String fecha) {
		Venta venta = ventaService.buscarVentaPorId(idventa);
		try{
			Timestamp fechaTS = Timestamp.valueOf(fecha);
			if (venta == null || fechaTS == null){
				return new ResponseEntity<>("La venta que se quiere editar no existe o falta el valor de la fecha",HttpStatus.BAD_REQUEST);
			}
			venta.setFecha(fechaTS);
			ventaService.actualizarVenta(idventa,venta);
			return new ResponseEntity<>("Edición exitosa",HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("El formato de la fecha no corresponde",HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{idventa}/cambiarTipoPrueba/{tipo}")
	public ResponseEntity<String> cambioTipoPrueba(@PathVariable int idventa, @PathVariable String tipo) {
		Venta venta = ventaService.buscarVentaPorId(idventa);
			if (venta == null || tipo == null){
				return new ResponseEntity<>("La venta que se quiere editar no existe o falta el valor del tipo de venta",HttpStatus.BAD_REQUEST);
			}
			venta.setTipoventa(tipo);
			ventaService.actualizarVenta(idventa,venta);
			return new ResponseEntity<>("Edición exitosa",HttpStatus.OK);
	}

	@PutMapping("/{idventa}/cambiarMetodoPagoPrueba/{metodo}")
	public ResponseEntity<String> cambioMetodoPagoPrueba(@PathVariable int idventa, @PathVariable String metodo) {
		Venta venta = ventaService.buscarVentaPorId(idventa);
		if (venta == null || metodo == null){
			return new ResponseEntity<>("La venta que se quiere editar no existe o falta el valor del metodo de pago de venta",HttpStatus.BAD_REQUEST);
		}
		venta.setMetodopago(metodo);
		ventaService.actualizarVenta(idventa,venta);
		return new ResponseEntity<>("Edición exitosa",HttpStatus.OK);
	}

	@GetMapping("/verRegistroVentasResumen")
	public ResponseEntity<?> verRegistroVentasResumen(@RequestParam String tipoventa){
		List<ViewRegistroVentasResumen> lista = ventaService.verRegistroVentaResumen(tipoventa);
		if (lista.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
}
