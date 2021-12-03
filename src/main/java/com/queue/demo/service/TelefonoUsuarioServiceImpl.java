package com.queue.demo.service;

import com.queue.demo.model.TelefonoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class TelefonoUsuarioServiceImpl implements TelefonoUsuarioService{

	@PersistenceContext
	private EntityManager em;

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

	@Override
	public List<TelefonoUsuario> buscarTelefonoPorRut(String rutUsuario) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TelefonoUsuario> criteriaQ = cb.createQuery(TelefonoUsuario.class);
		Root<TelefonoUsuario> root = criteriaQ.from(TelefonoUsuario.class);
		criteriaQ.select(root).where(cb.equal(root.get("rutusuario"), rutUsuario));
		return em.createQuery(criteriaQ).getResultList();
	}

}
