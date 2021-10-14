package com.queue.demo.service;

import com.queue.demo.model.TelefonoRepresentante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class TelefonoRepresentanteServiceImpl implements TelefonoRepresentanteService{
	
	@Autowired
	RepositorioTelefonoRepresentante repTelefonoRep;
	
	@Override
	public List<TelefonoRepresentante> buscarTodosLosTelefonos(){
		return repTelefonoRep.findAll();
	}
	@Override
    public TelefonoRepresentante buscarTelefonoPorRut(String rutRep) {
		return repTelefonoRep.findById(1).get();
    }
	@Override
    public void guardar(TelefonoRepresentante telefono) {
		repTelefonoRep.save(telefono);
    }
	@Override
    public void borrarTelefonoPorRut(String rut) {
		repTelefonoRep.deleteById(1);
    }
}
