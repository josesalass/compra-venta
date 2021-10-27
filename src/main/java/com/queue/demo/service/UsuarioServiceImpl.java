package com.queue.demo.service;

import com.queue.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.RepositorioUsuario;




@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	RepositorioUsuario repUsuario;
	
	@Override 
	public List<Usuario> buscarTodosLosUsuarios(){
		return repUsuario.findAll();
	}
	@Override 
    public Usuario buscarUsuarioPorRut(String rut) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQ = cb.createQuery(Usuario.class);
		Root<Usuario> root = criteriaQ.from(Usuario.class);
		criteriaQ.select(root).where(cb.equal(root.get("rutusuario"),rut));
		if(em.createQuery(criteriaQ).getResultList().isEmpty()) {
			return null;
		}
		return em.createQuery(criteriaQ).getResultList().get(0);
	}
	@Override 
    public void guardar(Usuario usuario) {
		repUsuario.save(usuario);
	}
	@Override 
    public void borrarUsuarioPorRut(String rut) {
		Usuario usuario = buscarUsuarioPorRut(rut);
		repUsuario.delete(usuario); 
	}
}
