package com.queue.demo.controller;

import com.queue.demo.model.Factura;
import com.queue.demo.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
    @Autowired
    FacturaService FacturaService;

    @GetMapping("")
    public List<Factura> list(){
        return FacturaService.buscarTodasLasFacturas();
    }
}
