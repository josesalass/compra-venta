package com.queue.demo.controller;

import com.queue.demo.model.Compra;
import com.queue.demo.model.ViewRegistroComprasDetalle;
import com.queue.demo.model.ViewRegistroComprasResumen;
import com.queue.demo.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {
    @Autowired
    CompraService compraService;
    
    @Autowired
    ProductoService productoService;
    
    @Autowired
    PerteneceACompraService perteneceACompraService;
    
    @GetMapping("")
    public ResponseEntity<List<Compra>> list(){
        List<Compra> compras = compraService.buscarTodasLasCompras();
        	if(!compras.isEmpty()){
                return new ResponseEntity<>(compras, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
    
    
    @PostMapping("/guardarCompra")
    public ResponseEntity<?> saveCompra(@RequestBody Compra compra) {
        try{
            compraService.guardarCompra(compra);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
    


    @PutMapping("/{idcompra}/cambiarFecha/{fecha}")
    public ResponseEntity<String> cambiarFecha(@PathVariable int idcompra, @PathVariable String fecha) {
        Compra compra = compraService.buscarCompraPorId(idcompra);
        try{
            Timestamp fechaTs = Timestamp.valueOf(fecha);
            if (compra == null || fechaTs == null) {
                return new ResponseEntity<>("Debe ingresar un valor a la fecha ",HttpStatus.BAD_REQUEST);
            }
            compra.setFecha(fechaTs);
            compraService.actualizarCompra(idcompra, compra);
            return new ResponseEntity<>("Cambio exitoso" ,HttpStatus.OK );

        }catch (IllegalArgumentException e){
            return new ResponseEntity<>("Debe ingresar un formato valido para la fecha",HttpStatus.BAD_REQUEST);
        }

    }

  	@RequestMapping("/editEmpresa")
  	public void editarRutEmpresa (@RequestParam(value="idcompra",required=true) int idcompra, @RequestParam(value="rutempresa",required=true) String rutempresa){
  		compraService.editarRutEmpresa(rutempresa, idcompra);
  	}

    @PutMapping("/{idcompra}/cambiarEmpresa/{rutempresa}")
    public ResponseEntity<String> cambiarRutEmpresa(@PathVariable int idcompra, @PathVariable String rutempresa) {
        Compra compra = compraService.buscarCompraPorId(idcompra);

            if (compra == null || rutempresa == null) {
                return new ResponseEntity<>("Debe ingresar un valor al rut ",HttpStatus.BAD_REQUEST);
            }
            compra.setRutempresa(rutempresa);
            compraService.actualizarCompra(idcompra, compra);
            return new ResponseEntity<>("Cambio exitoso" ,HttpStatus.OK );

    }
    @GetMapping("/verRegistroComprasResumen")
    public ResponseEntity<?> verRegistroComprasResumen(){
        List<ViewRegistroComprasResumen> lista = compraService.verRegistroCompraResumen();
        if (lista.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);

    }

    @GetMapping("/verRegistroComprasDetalle")
    public ResponseEntity<?> verRegistroComprasDetalle(){
        List<ViewRegistroComprasDetalle> lista = compraService.verRegistroCompraDetalle();
        if (lista.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);

    }
}
