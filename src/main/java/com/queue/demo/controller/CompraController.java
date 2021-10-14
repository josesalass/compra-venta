package com.queue.demo.controller;

import com.queue.demo.model.Compra;
import com.queue.demo.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {
    @Autowired
    CompraService compraService;

    @GetMapping("")
    public List<Compra> list(){
        return compraService.buscarTodasLasCompras();
    }
}
