package com.queue.demo.service;

import com.queue.demo.model.RepresentanteProveedor;
import com.queue.demo.model.TelefonoRepresentante;
import com.queue.demo.model.TelefonoUsuario;
import com.queue.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.queue.demo.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
@Transactional
public class TelefonoRepresentanteServiceImpl implements TelefonoRepresentanteService{
	@PersistenceContext
	private EntityManager em;

	@Autowired
	RepositorioTelefonoRepresentante repTelefonoRep;
	@Autowired
	RepositorioRepresentanteProveedor repositorioRepresentanteProveedor;
	@Autowired
	RepresentanteProveedorServiceImpl 	representanteProveedorService;
	
	@Override
	public List<TelefonoRepresentante> buscarTodosLosTelefonos(){
		return repTelefonoRep.findAll();
	}
	@Override
    public List<TelefonoRepresentante> buscarTelefonoPorRut(String rutRep) throws Exception {
		if(rutRep==null){
			throw new Exception("Rut Representante es NULL");
		}
		Optional<RepresentanteProveedor> u =representanteProveedorService.buscarRepresentantePorRut(rutRep);
		if (u.isEmpty()){
			throw new Exception("No se encontro Representante con ese RUT");
		}
		return u.get().getTelefonosRepresentante();
    }
	@Override
    public TelefonoRepresentante guardar(TelefonoRepresentante telefono) throws Exception {
		if(telefono==null){
			throw new Exception("Los datos ingresados son NULL");
		}
		String rutrep= telefono.getRutrep();
		List<TelefonoRepresentante> tsf=buscarTelefonoPorRut(rutrep);
		List<TelefonoRepresentante> telefonoexist= buscarTodosLosTelefonos();
		if(telefonoexist.contains(telefono.getTelefono())){
			throw new Exception("El Telefono ya existe");
		}
		Optional<RepresentanteProveedor> u =representanteProveedorService.buscarRepresentantePorRut(rutrep);
		if (u.isEmpty()){
			throw new Exception("No se encontro usuario con ese RUT");
		}
		return repTelefonoRep.save(telefono);
    }
	@Override
    public void borrarTelefonoPorRut(String rut) {
		repTelefonoRep.deleteByrutrep(rut);
    }
}
