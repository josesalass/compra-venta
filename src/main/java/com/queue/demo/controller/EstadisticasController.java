package com.queue.demo.controller;

import com.queue.demo.model.*;
import com.queue.demo.model.ViewPromedioVentasMes;
import com.queue.demo.model.ViewProductoMenosVendidoPorMes;
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

    @GetMapping("/verproductomenosvendido")
    public ResponseEntity<?> getProductoMenosVendido(@RequestParam(value="año",required = true) int año){
        List<ViewProductoMenosVendidoPorMes> lista = estadisticasService.verProductoMenosVendido(año);
        if (lista.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @GetMapping("/productomasvendido")
    public ResponseEntity<?> getProductoMasVendido(@RequestParam(value = "año",required = true)int año){
        List<ViewProductoMasVendidoPorMes> lista = estadisticasService.verProductoMasVendido(año);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
