package com.queue.demo.controller;

import com.queue.demo.model.Asociada_Venta;
import com.queue.demo.service.Asociada_VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asociada_Venta")
public class Asociada_VentaController {
    @Autowired
    Asociada_VentaService asociada_VentaService;

    @GetMapping("")
    public ResponseEntity<List<Asociada_Venta>> list(){
        List<Asociada_Venta> avs = asociada_VentaService.buscarTodasLasVentasAsociadas();
        if (!avs.isEmpty()){
            return new ResponseEntity<>(avs, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
