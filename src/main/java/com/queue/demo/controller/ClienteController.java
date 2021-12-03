package com.queue.demo.controller;

import com.queue.demo.model.Cliente;
import com.queue.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping("")
    public ResponseEntity<List<Cliente>> list(){
        List<Cliente> clientes = clienteService.buscarTodosLosClientes();
        if(!clientes.isEmpty()){
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
