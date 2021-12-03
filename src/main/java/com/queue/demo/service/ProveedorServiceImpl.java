package com.queue.demo.service;

import com.queue.demo.model.Proveedor;
import com.queue.demo.model.RepresentanteProveedor;
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
import java.util.NoSuchElementException;
import java.util.Optional;

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
	public Optional<Proveedor> buscarPorNombre (String nombre){
		// utilizamos CriteriaBuilder para buscar a los proveedores mediante una CriteriaQuery  
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Proveedor> criteriaQ = criteriaBuilder.createQuery(Proveedor.class);
		Root<Proveedor> root = criteriaQ.from(Proveedor.class);
		criteriaQ.select(root).where(criteriaBuilder.equal(root.get("nombreempresa"), nombre));
		List<Proveedor> resp= em.createQuery(criteriaQ).getResultList();

		if(!resp.isEmpty()){
			return Optional.of(resp.get(0));
		}
		return Optional.empty();
	}
	
	@Override
	// metodo en el cual buscamos a los proveedores por su rut
	public Optional<Proveedor> buscarPorRut (String rut){
		// utilizamos CriteriaBuilder para buscar a los proveedores mediante una CriteriaQuery 
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Proveedor> criteriaQ = cb.createQuery(Proveedor.class);
		Root<Proveedor> root = criteriaQ.from(Proveedor.class);
		criteriaQ.select(root).where(cb.equal(root.get("rutempresa"), rut));
		List<Proveedor> resp= em.createQuery(criteriaQ).getResultList();
		if(!resp.isEmpty()){
			return Optional.of(resp.get(0));
		}
		return Optional.empty();

	}
	
	@Override
	// metodo en el cual eliminamos(la eliminacion es de manera logica) a un proveedor por su nombre 
	public boolean eliminarPorNombre(String nombre) {
		try{
			Proveedor proveedor=buscarPorNombre(nombre).get();
			proveedor.setEstado(false);

			RepresentanteProveedor representanteProveedor = proveedor.getRepresentanteProveedor();
			representanteProveedor.setEstado(false);

			repRepProveedor.save(representanteProveedor);
			repProveedor.save(proveedor);

			return true;

		}catch(NoSuchElementException e) {
			return false;
		}

		
	}
	@Override
	// metodo en el cual eliminamos(la eliminacion es de manera logica) a un proveedor por su nombre
	public boolean eliminarPorRut(String rut) {
		try{

			Proveedor proveedor = buscarPorRut(rut).get();
			proveedor.setEstado(false);

			RepresentanteProveedor representanteProveedor = proveedor.getRepresentanteProveedor();
			representanteProveedor.setEstado(false);

			repProveedor.save(proveedor);
			repRepProveedor.save(representanteProveedor);

			return true;
		}catch(NoSuchElementException e) {
			// se comunica con el frontend
			return false;
		}

	}
	
	
	@Override
	// metodo en el cual se buscan a todos los proveedores
	public List<Proveedor> buscarTodosLosProveedores(){
		return repProveedor.findAll();
	}


	@Override
	public Proveedor guardarProveedor(Proveedor proveedor) throws Exception{
		if(proveedor==null || buscarPorRut(proveedor.getRutempresa()).isPresent()){
			throw new Exception("Ya existente");
		}
		return repProveedor.save(proveedor);
	}
}
