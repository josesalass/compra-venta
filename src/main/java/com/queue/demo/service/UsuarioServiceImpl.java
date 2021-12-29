

package com.queue.demo.service;


import com.queue.demo.model.Usuario;
import com.queue.demo.util.Encriptador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.Key;
import java.util.List;
import java.util.Optional;

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
	public Optional<Usuario> buscarUsuarioPorRut(String idusuario) {
		Optional<Usuario> optional = repUsuario.findById(idusuario);
		if (optional.isPresent()) {
			return Optional.of(optional.get());
		}
		return Optional.empty();
	}

	@Override
	public Usuario guardar(Usuario usuario) throws Exception {
		if(usuario==null){
			throw new Exception();
		}
		Key key = Encriptador.generateKey();
		String contraseniaEncriptada = Encriptador.encrypt(usuario.getContrasenia(),key);
		usuario.setContrasenia(contraseniaEncriptada);
		return repUsuario.save(usuario);
	}

	@Override
	public void borrarUsuarioPorRut(String rut) {
		repUsuario.deleteById(rut);
	}
}
