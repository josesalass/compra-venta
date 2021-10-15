package com.queue.demo.service;

import com.queue.demo.model.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class FacturaServiceImpl implements FacturaService{

    @Autowired
    RepositorioFactura repFactura;

    @Override
    public List<Factura> buscarTodasLasFacturas() {
        return repFactura.findAll();
    }

    @Override
    public Factura buscarFacturaPorId(int idVenta) {
        return repFactura.findById(1).get();
    }

    @Override
    public void guardar(Factura factura) {
    	repFactura.save(factura);
    }

    @Override
    public void borrarFacturaPorId(int idVenta) {
    	repFactura.deleteById(1);
    }
    
}