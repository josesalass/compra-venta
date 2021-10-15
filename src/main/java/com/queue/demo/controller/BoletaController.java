package com.queue.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.queue.demo.model.Boleta;
import com.queue.demo.service.BoletaService;

@RestController
@RequestMapping("/boleta")
public class BoletaController {
	@Autowired
    BoletaService boletaService;

    @GetMapping("")
    public List<Boleta> list(){
        return boletaService.buscarTodasLasBoletas();
    }
}
