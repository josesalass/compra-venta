package com.queue.demo.service;

import com.queue.demo.model.ViewPromedioVentasMes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EstadisticasServiceTest {

    @Mock
    private EntityManager entityManager;
    @Mock
    private CriteriaBuilder criteriaBuilder;
    @Mock
    private CriteriaQuery criteriaQuery=mock(CriteriaQuery.class);
    @Mock
    private Root root;

    @Mock
    private TypedQuery typedQuery = mock(TypedQuery.class);

    @InjectMocks
    private EstadisticasServiceImpl estadisticasService;

    @Test
    void siInvocoVerPromedioVentasDebeRetornarViewPromedioVentasMesList(){
        List<ViewPromedioVentasMes> resultado;
        List<ViewPromedioVentasMes> mocklist = getViewPromedioVentasMes();
        String comparar = "2021%";

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewPromedioVentasMes.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.like(root.get("anio_mes"),comparar))).thenReturn(criteriaQuery);


        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        resultado = estadisticasService.verPromedioVentas(2021);

        assertNotNull(resultado);
        assertEquals(resultado.get(0),mocklist.get(0));
    }

    private List<ViewPromedioVentasMes> getViewPromedioVentasMes() {
        List<ViewPromedioVentasMes> p = new ArrayList<>();
        ViewPromedioVentasMes v = new ViewPromedioVentasMes();
        v.setAnio_mes("2021-01");
        v.setPromedio_mensual(30000);
        p.add(v);
        v.setAnio_mes("2021-02");
        v.setPromedio_mensual(1000);
        p.add(v);
        return p;
    }

}
