package com.queue.demo.service;

import com.queue.demo.model.TelefonoRepresentante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
	
	@Override
	public List<TelefonoRepresentante> buscarTodosLosTelefonos(){
		return repTelefonoRep.findAll();
	}
	@Override
    public List<TelefonoRepresentante> buscarTelefonoPorRut(String rutRep) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TelefonoRepresentante> criteriaQ = cb.createQuery(TelefonoRepresentante.class);
		Root<TelefonoRepresentante> root = criteriaQ.from(TelefonoRepresentante.class);
		criteriaQ.select(root).where(cb.equal(root.get("rutrep"), rutRep));
		return em.createQuery(criteriaQ).getResultList();
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
