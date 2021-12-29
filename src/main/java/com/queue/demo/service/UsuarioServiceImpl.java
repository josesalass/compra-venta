

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
		Key key = Encriptador.generateKey();  //Key para encriptar
		String rut = usuario.getRutusuario();
		String contra = usuario.getContrasenia();
		String contra2 = rut.concat(contra);  //Concateno el rut y la contraseña para encriptarla
		String contraseniaEncriptada = Encriptador.encrypt(contra2,key);
		usuario.setContrasenia(contraseniaEncriptada);
		return repUsuario.save(usuario);  //se guarda como contraseña el rut y la contraseña puesta
	}

	@Override
	public void borrarUsuarioPorRut(String rut) {
		repUsuario.deleteById(rut);
	}
}
