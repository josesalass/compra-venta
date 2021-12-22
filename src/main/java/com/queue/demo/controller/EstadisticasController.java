package com.queue.demo.controller;

import com.queue.demo.model.ViewPromedioVentasMes;
import com.queue.demo.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    @Autowired
    EstadisticasService estadisticasService;

    @GetMapping("/promedioventas")
    public ResponseEntity<?> getPromedioVentas(@RequestParam(value="año",required = true) int año){
        List<ViewPromedioVentasMes> lista = estadisticasService.verPromedioVentas(año);
        if (lista.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }
}
