package com.queue.demo.service;

import com.queue.demo.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import com.queue.demo.repository.*;

@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService{
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	RepositorioProveedor repProveedor;
	@Autowired
	RepositorioRepresentanteProveedor repRepProveedor;
	@Autowired
	RepositorioTelefonoRepresentante repTelefonoRep;
	
	@Override
	// metodo en el cual buscamos a los proveedores por su nombre
	public List <Proveedor> buscarPorNombre (String nombre){
		// utilizamos CriteriaBuilder para buscar a los proveedores mediante una CriteriaQuery  
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Proveedor> criteriaQ = cb.createQuery(Proveedor.class);
		Root<Proveedor> root = criteriaQ.from(Proveedor.class);
		criteriaQ.select(root).where(cb.equal(root.get("nombreempresa"), nombre));
		return em.createQuery(criteriaQ).getResultList();

	}
	
	@Override
	// metodo en el cual buscamos a los proveedores por su rut
	public List <Proveedor> buscarPorRut (String rut){
		// utilizamos CriteriaBuilder para buscar a los proveedores mediante una CriteriaQuery 
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Proveedor> criteriaQ = cb.createQuery(Proveedor.class);
		Root<Proveedor> root = criteriaQ.from(Proveedor.class);
		criteriaQ.select(root).where(cb.equal(root.get("rutempresa"), rut));
		return em.createQuery(criteriaQ).getResultList();

	}
	
	@Override
	// metodo en el cual eliminamos(la eliminacion es de manera logica) a un proveedor por su nombre 
	public void eliminarPorNombre(String nombre) {
		
		try{
			
			String rut=buscarPorNombre(nombre).get(0).getRutempresa();
			repRepProveedor.ocultarPorRut(rut);
			repProveedor.ocultarPorNombre(nombre);
			
		}catch(DataAccessException e) {
			// se comunica con el frontend
		}catch(IndexOutOfBoundsException e) {
			// se comunica con el frontend
		}
		
	}
	@Override
	// metodo en el cual eliminamos(la eliminacion es de manera logica) a un proveedor por su nombre
	public void eliminarPorRut(String rut) {
		
		try{
			
			repRepProveedor.ocultarPorRut(rut);
			repProveedor.ocultarPorRut(rut);
			
		}catch(DataAccessException e) {
			// se comunica con el frontend
		}catch(NullPointerException e) {
			// se comunica con el frontend
		}

	}
	
	
	@Override
	// metodo en el cual se buscan a todos los proveedores
	public List<Proveedor> buscarTodosLosProveedores(){
		return repProveedor.findAll();
	}


	@Override
	public Proveedor guardarProveedor(Proveedor proveedor) {
		return repProveedor.save(proveedor);
		
	}
}
