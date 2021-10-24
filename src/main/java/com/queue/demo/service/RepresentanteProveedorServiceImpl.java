package com.queue.demo.service;

import com.queue.demo.model.Cliente;
import com.queue.demo.model.RepresentanteProveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class RepresentanteProveedorServiceImpl implements RepresentanteProveedorService{

    @Autowired
    RepositorioRepresentanteProveedor repRepresentanteProveedor;

    @Override
    public List<RepresentanteProveedor> buscarTodosLosRepresentanteProveedor() {
        return repRepresentanteProveedor.findAll();
    }

    @Override
    public RepresentanteProveedor buscarRepresentantePorRut(String rut) {
        return repRepresentanteProveedor.findById(1).get();
    }

    @Override
    public void guardar(RepresentanteProveedor RepresentanteProveedor) {
    	repRepresentanteProveedor.save(RepresentanteProveedor);
    }

    
    @Override
    public void eliminarRepresentanteProveedorPorRut(String rutRep){
    	repRepresentanteProveedor.deleteById(1);
    }
}
