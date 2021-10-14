package com.queue.demo.service;

import com.queue.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.RepositorioUsuario;




@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	RepositorioUsuario repUsuario;
	
	@Override 
	public List<Usuario> buscarTodosLosUsuarios(){
		return repUsuario.findAll();
	}
	@Override 
    public Usuario buscarUsuarioPorRut(String rut) {
		return repUsuario.findById(1).get();
	}
	@Override 
    public void guardar(Usuario usuario) {
		repUsuario.save(usuario);
	}
	@Override 
    public void borrarUsuarioPorRut(String rut) {
		repUsuario.deleteById(1);
	}
}
