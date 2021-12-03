package com.queue.demo.service;

import java.util.List;
import java.util.Optional;

import com.queue.demo.model.RepresentanteProveedor;

public interface RepresentanteProveedorService {
    public List<RepresentanteProveedor> buscarTodosLosRepresentanteProveedor();
    public Optional<RepresentanteProveedor> buscarRepresentantePorRut(String rut) ;
    public RepresentanteProveedor guardar(RepresentanteProveedor representanteProveedor)throws Exception ;
    public void eliminarRepresentanteProveedorPorRut(String rutRep);
}
