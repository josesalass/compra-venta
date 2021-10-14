package com.queue.demo.controller;

import com.queue.demo.model.Asociada_Venta;
import com.queue.demo.service.Asociada_VentaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Asociada_Venta> list(){
        return asociada_VentaService.buscarTodasLasVentasAsociadas();
    }
}
