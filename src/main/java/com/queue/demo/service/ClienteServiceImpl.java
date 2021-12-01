package com.queue.demo.service;

import com.queue.demo.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import com.queue.demo.repository.*;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    RepositorioCliente repCliente;

    @Override
    public List<Cliente> buscarTodosLosClientes() {
        return repCliente.findAll();
    }

    @Override
    public Optional<Cliente> buscarClientePorRut(String rut) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQ = cb.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQ.from(Cliente.class);
        criteriaQ.select(root).where(cb.equal(root.get("rutcliente"),rut));
        if (em.createQuery(criteriaQ).getResultList().isEmpty()){
            return Optional.empty();
        }
        return Optional.of(em.createQuery(criteriaQ).getResultList().get(0));
    }

    @Override
    public Optional<Cliente> guardar(Cliente cliente) {
        return Optional.of(repCliente.save(cliente));
    }

    @Override
    public boolean borrarClientePorRut(String rut) {
        Optional<Cliente> cliente = buscarClientePorRut(rut);
        if(!cliente.isEmpty()){
            repCliente.delete(cliente.get());
            return true;
        }
        return false;
    }
}
