package com.queue.demo.service;

import com.queue.demo.model.Cliente;
import com.queue.demo.model.Proveedor;
import com.queue.demo.model.RepresentanteProveedor;
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
public class RepresentanteProveedorServiceImpl implements RepresentanteProveedorService{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    RepositorioRepresentanteProveedor repRepresentanteProveedor;

    @Override
    public List<RepresentanteProveedor> buscarTodosLosRepresentanteProveedor() {
        return repRepresentanteProveedor.findAll();
    }

    @Override
    public RepresentanteProveedor buscarRepresentantePorRut(String rut) {
        // utilizamos CriteriaBuilder para buscar a los representantes mediante una CriteriaQuery
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RepresentanteProveedor> criteriaQ = cb.createQuery(RepresentanteProveedor.class);
        Root<RepresentanteProveedor> root = criteriaQ.from(RepresentanteProveedor.class);
        criteriaQ.select(root).where(cb.equal(root.get("rutrep"), rut));
        return em.createQuery(criteriaQ).getResultList().get(0);
    }

    @Override
    public void guardar(RepresentanteProveedor RepresentanteProveedor) {
    	repRepresentanteProveedor.save(RepresentanteProveedor);
    }

    
    @Override
    public void eliminarRepresentanteProveedorPorRut(String rutRep){
    	repRepresentanteProveedor.deleteById(1);
    }
}
