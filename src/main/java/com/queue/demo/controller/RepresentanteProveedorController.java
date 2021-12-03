package com.queue.demo.controller;

import com.queue.demo.model.Compra;
import com.queue.demo.model.RepresentanteProveedor;
import com.queue.demo.service.RepresentanteProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/representantes")
public class RepresentanteProveedorController {
    @Autowired
    RepresentanteProveedorService representanteproveedorService;

    @GetMapping("")
    public ResponseEntity<List<RepresentanteProveedor>> list(){
        List<RepresentanteProveedor> repProv = representanteproveedorService.buscarTodosLosRepresentanteProveedor();
        if(!repProv.isEmpty()){
            return new ResponseEntity<>(repProv, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
