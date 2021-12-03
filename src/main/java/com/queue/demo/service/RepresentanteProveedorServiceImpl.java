package com.queue.demo.service;

import com.queue.demo.model.RepresentanteProveedor;
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
    public Optional<RepresentanteProveedor> buscarRepresentantePorRut(String rut) {
        // utilizamos CriteriaBuilder para buscar a los representantes mediante una CriteriaQuery
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RepresentanteProveedor> criteriaQ = cb.createQuery(RepresentanteProveedor.class);
        Root<RepresentanteProveedor> root = criteriaQ.from(RepresentanteProveedor.class);
        criteriaQ.select(root).where(cb.equal(root.get("rutrep"), rut));
        List<RepresentanteProveedor> reppro=em.createQuery(criteriaQ).getResultList();
        if(!reppro.isEmpty()){
           return Optional.of(reppro.get(0));
        }
        return Optional.empty();
    }

    @Override
    public RepresentanteProveedor guardar(RepresentanteProveedor representanteProveedor) throws Exception {
        if(representanteProveedor==null){
            throw new Exception();
        }
    	return repRepresentanteProveedor.save(representanteProveedor);
    }

    
    @Override
    public void eliminarRepresentanteProveedorPorRut(String rutRep){

        repRepresentanteProveedor.deleteByrutrep(rutRep);
    }
}
