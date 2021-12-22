package com.queue.demo.controller;

import com.queue.demo.model.Compra;
import com.queue.demo.model.ViewEgresosPorMes;
import com.queue.demo.model.ViewFlujoDeCaja;
import com.queue.demo.model.ViewIngresosPorMes;
import com.queue.demo.service.FlujoDeCajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flujodecaja")
public class FlujoDeCajaController {
    @Autowired
    FlujoDeCajaService flujoDeCajaService;

    @GetMapping("")
    public ResponseEntity<List<ViewFlujoDeCaja>> listFlujo(){
        List<ViewFlujoDeCaja> flujoDeCaja = flujoDeCajaService.verFlujoDeCaja();
        if(!flujoDeCaja.isEmpty()){
            return new ResponseEntity<>(flujoDeCaja, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/ingresos")
    public ResponseEntity<List<ViewIngresosPorMes>> listIngresos(){
        List<ViewIngresosPorMes> ingresosPorMes = flujoDeCajaService.verIngresosAnuales();
        if(!ingresosPorMes.isEmpty()){
            return new ResponseEntity<>(ingresosPorMes, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/egresos")
    public ResponseEntity<List<ViewEgresosPorMes>> listEgresos(){
        List<ViewEgresosPorMes> egresosPorMes = flujoDeCajaService.verEgresosAnuales();
        if(!egresosPorMes.isEmpty()){
            return new ResponseEntity<>(egresosPorMes, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
