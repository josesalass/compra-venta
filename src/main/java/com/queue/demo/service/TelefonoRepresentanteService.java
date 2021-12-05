package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.TelefonoRepresentante;

public interface TelefonoRepresentanteService {
	public List<TelefonoRepresentante> buscarTodosLosTelefonos();
    public List<TelefonoRepresentante> buscarTelefonoPorRut(String rutRep) throws Exception;
    public TelefonoRepresentante guardar(TelefonoRepresentante telefono) throws Exception;
    public void borrarTelefonoPorRut(String rut);
}
