package com.queue.demo.service;

import com.queue.demo.model.ViewPromedioVentasMes;
import com.queue.demo.model.ViewProductoMenosVendidoPorMes;
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
public class EstadisticasServiceImpl implements EstadisticasService{
    @PersistenceContext
    private EntityManager em;

    public List<ViewPromedioVentasMes> verPromedioVentas(int año){
        String comparar = String.valueOf(año) + "%";
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root root = cq.from(ViewPromedioVentasMes.class);
        cq.select(root).where(cb.like(root.get("anio_mes"),comparar));
        return em.createQuery(cq).getResultList();
    }
    public List<ViewProductoMenosVendidoPorMes> verProductoMenosVendido(int año){
        String comparar = String.valueOf(año) + "%";
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root root = cq.from(ViewProductoMenosVendidoPorMes.class);
        cq.select(root).where(cb.like(root.get("fecha"),comparar));
        return em.createQuery(cq).getResultList();
    }
}
