package com.queue.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.queue.demo.model.PerteneceACompra;
import com.queue.demo.service.PerteneceACompraService;

@RestController
@RequestMapping("/pertenececompra")
public class PerteneceACompraController {
	
	@Autowired
	PerteneceACompraService PerteneceACompraService;
	
	@GetMapping("")
    public List<PerteneceACompra> list(){
        return PerteneceACompraService.ListarTodasLasCompras();
    }
	
	@RequestMapping(method=RequestMethod.POST, value="/guardarpcompra")
    public ResponseEntity<PerteneceACompra> addCompra(@RequestBody PerteneceACompra perteneceacompra) {
        try{
            //PerteneceACompra guardaPerteneceACompra= PerteneceACompraService.guardarPerteneceACompra( perteneceacompra);
            return new ResponseEntity<>(PerteneceACompraService.guardarPerteneceACompra( perteneceacompra), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
}
