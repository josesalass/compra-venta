package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.RepresentanteProveedor;

public interface RepresentanteProveedorService {
    public List<RepresentanteProveedor> buscarTodosLosRepresentanteProveedor();
    public RepresentanteProveedor buscarRepresentantePorRut(String rut);
    public void guardar(RepresentanteProveedor RepresentanteProveedor);
    public void eliminarRepresentanteProveedorPorRut(String rutRep);
}
