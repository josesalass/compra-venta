package com.queue.demo.service;

import com.queue.demo.model.TelefonoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class TelefonoUsuarioImpl implements TelefonoUsuarioService{
	
	@Autowired
	RepositorioTelefonoUsuario repTelefonoUsuario;
	
	@Override
	public List<TelefonoUsuario> buscarTodosLosTelefonosUsuarios(){
		return repTelefonoUsuario.findAll();
	}
	
	@Override
	public TelefonoUsuario buscarTelefonoUsuarioPorId(int id) {
		return repTelefonoUsuario.findById(id).get();
	}
	
	@Override
	public void guardar(TelefonoUsuario telefonoU) {
		repTelefonoUsuario.save(telefonoU);
	}
	
	@Override
	public void borrarTelefonoUsuarioPorId(int telefonoU) {
		repTelefonoUsuario.deleteById(telefonoU);
	}
}
