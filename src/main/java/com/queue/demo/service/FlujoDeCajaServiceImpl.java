package com.queue.demo.service;

import com.queue.demo.model.ViewEgresosPorMes;
import com.queue.demo.model.ViewFlujoDeCaja;
import com.queue.demo.model.ViewIngresosPorMes;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FlujoDeCajaServiceImpl implements FlujoDeCajaService{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ViewIngresosPorMes> verIngresosAnuales(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root root = cq.from(ViewIngresosPorMes.class);
        cq.select(root);

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<ViewEgresosPorMes> verEgresosAnuales(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
            Root root = cq.from(ViewEgresosPorMes.class);
        cq.select(root);

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<ViewFlujoDeCaja> verFlujoDeCaja(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root root = cq.from(ViewFlujoDeCaja.class);
        cq.select(root);

        return em.createQuery(cq).getResultList();
    }


}
