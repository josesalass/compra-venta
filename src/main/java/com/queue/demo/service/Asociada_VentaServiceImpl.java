package com.queue.demo.service;

import com.queue.demo.model.Asociada_Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class Asociada_VentaServiceImpl implements Asociada_VentaService{

    @Autowired
    RepositorioAsociada_Venta repAsociada_Venta;

    @Override
    public List<Asociada_Venta> buscarTodasLasVentasAsociadas() {
        return repAsociada_Venta.findAll();
    }
    @Override
    public void saveAsociadaVenta(Asociada_Venta venta) {
    	repAsociada_Venta.save(venta);
    }
}
