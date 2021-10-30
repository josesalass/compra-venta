package com.queue.demo.controller;

import com.queue.demo.model.Compra;
import com.queue.demo.service.CompraService;
import com.queue.demo.service.PerteneceACompraService;
import com.queue.demo.service.ProductoService;

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
@RequestMapping("/compras")
public class CompraController {
    @Autowired
    CompraService compraService;
    
    @Autowired
    ProductoService productoService;
    
    @Autowired
    PerteneceACompraService perteneceACompraService;
    
    @GetMapping("")
    public List<Compra> list(){
        return compraService.buscarTodasLasCompras();
    }
    
    
    @PostMapping("/guardarCompra")
    public ResponseEntity<?> saveCompra(@RequestBody Compra compra) {
        return new ResponseEntity<>(compraService.guardarCompra(compra), HttpStatus.CREATED);
    }
    
    
    @RequestMapping("/editFecha")
  	public void editarFecha (@RequestParam(value="idcompra",required=true) int idcompra, @RequestParam(value="fecha",required=true) Timestamp fecha){
  		compraService.editarFecha(fecha, idcompra);
  	}
  	    
  	@RequestMapping("/editEmpresa")
  	public void editarRutEmpresa (@RequestParam(value="idcompra",required=true) int idcompra, @RequestParam(value="rutempresa",required=true) String rutempresa){
  		compraService.editarRutEmpresa(rutempresa, idcompra);
  	}
}
