package com.queue.demo.service;

import com.queue.demo.model.Proveedor;
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
public class ProveedorServiceImpl implements ProveedorService{
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	RepositorioProveedor repProveedor;
	
	@Override
	public List <Proveedor> buscarPorNombre (String nombre){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Proveedor> criteriaQ = cb.createQuery(Proveedor.class);
		Root<Proveedor> root = criteriaQ.from(Proveedor.class);
		
		criteriaQ.select(root).where(cb.equal(root.get("nombreempresa"), nombre));
		
		return em.createQuery(criteriaQ).getResultList();

	}
	
	@Override
	public void eliminarPorNombre(String nombre) {
		
		repProveedor.deleteBynombreempresa(nombre);
		
	}
	@Override
	public void eliminarPorRut(String rut) {
		
		repProveedor.deleteByrutempresa(rut);
		
	}
	
	@Override
	public List <Proveedor> buscarPorRut (String rut){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Proveedor> criteriaQ = cb.createQuery(Proveedor.class);
		Root<Proveedor> root = criteriaQ.from(Proveedor.class);
		
		criteriaQ.select(root).where(cb.equal(root.get("rutempresa"), rut));
		
		return em.createQuery(criteriaQ).getResultList();

	}
	
	@Override
	public List<Proveedor> buscarTodosLosProveedores(){
		return repProveedor.findAll();
	}

	@Override
	public void guardar(Proveedor proveedor) {
		repProveedor.save(proveedor);
	}
	@Override
	public void borrarProveedorPorRut(String rut) {
		repProveedor.deleteById(1);
	}
}
