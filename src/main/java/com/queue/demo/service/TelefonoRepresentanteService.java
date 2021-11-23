package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.TelefonoRepresentante;

public interface TelefonoRepresentanteService {
	public List<TelefonoRepresentante> buscarTodosLosTelefonos();
    public List<TelefonoRepresentante> buscarTelefonoPorRut(String rutRep);
    public void guardar(TelefonoRepresentante telefono);
    public void borrarTelefonoPorRut(String rut);
}
