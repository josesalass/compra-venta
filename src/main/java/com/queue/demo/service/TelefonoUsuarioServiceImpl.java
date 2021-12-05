package com.queue.demo.service;

import com.queue.demo.model.TelefonoUsuario;
import com.queue.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.queue.demo.repository.*;

@Service
@Transactional
public class TelefonoUsuarioServiceImpl implements TelefonoUsuarioService{

	@PersistenceContext
	private EntityManager em;

	@Autowired
	RepositorioTelefonoUsuario repTelefonoUsuario;

	@Autowired
	UsuarioServiceImpl 	usuarioService;

	@Override
	public List<TelefonoUsuario> buscarTodosLosTelefonosUsuarios(){

		return repTelefonoUsuario.findAll();
	}
	
	@Override
	public Optional <TelefonoUsuario> buscarTelefonoUsuarioPorId(int id) {
		Optional <TelefonoUsuario> telefono= repTelefonoUsuario.findById(id);
		if (telefono.isPresent()){
			return Optional.of(telefono.get());
		}
		return Optional.empty();

	}
	
	@Override
	public TelefonoUsuario guardar(TelefonoUsuario telefonoU)throws Exception {
		if(telefonoU==null){
			throw new Exception("Los datos ingresados son NULL");
		}
		String rutusuario= telefonoU.getRutusuario();
		List<TelefonoUsuario> tsf=buscarTelefonoPorRut(rutusuario);
		List<TelefonoUsuario> telefonoexist= buscarTodosLosTelefonosUsuarios();
		if(telefonoexist.contains(telefonoU.getTelefono())){
			throw new Exception("El Telefono ya existe");
		}
		Optional<Usuario> u = usuarioService.buscarUsuarioPorRut(rutusuario);
		if (u.isEmpty()){
			throw new Exception("No se encontro usuario con ese RUT");
		}

		return repTelefonoUsuario.save(telefonoU);
	}
	
	@Override
	public void  borrarTelefonoUsuarioPorId(int telefonoU) {

			repTelefonoUsuario.deleteById(telefonoU);
	}

	@Override
	public List<TelefonoUsuario> buscarTelefonoPorRut(String rutUsuario) throws Exception {
		if(rutUsuario==null){
			throw new Exception("Rut Usuario es NULL");
		}
		Optional<Usuario> u = usuarioService.buscarUsuarioPorRut(rutUsuario);
		if (u.isEmpty()){
			throw new Exception("No se encontro usuario con ese RUT");
		}
		return u.get().getTelefonosusuario();

	}

}
