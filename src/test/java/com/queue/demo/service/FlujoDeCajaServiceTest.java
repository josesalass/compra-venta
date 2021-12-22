package com.queue.demo.service;

import com.queue.demo.model.ViewEgresosPorMes;
import com.queue.demo.model.ViewFlujoDeCaja;
import com.queue.demo.model.ViewIngresosPorMes;
import com.queue.demo.model.ViewRegistroVentasResumen;
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
public class FlujoDeCajaServiceTest {


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
    private FlujoDeCajaServiceImpl flujoDeCajaService;

    @Test
    void SiInvocoVerIngresosAnualesYExistenIngresosRetornaListaDeIngresos(){
        List<ViewIngresosPorMes> resultado;

        List<ViewIngresosPorMes> ingresos = getIngresos();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewIngresosPorMes.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(ingresos);

        resultado = flujoDeCajaService.verIngresosAnuales();


        assertNotNull(resultado);
        assertEquals(ingresos.get(0),resultado.get(0));
    }

    @Test
    void SiInvocoVerIngresosAnualesYNoExistenIngresosRetornaListaVacia(){
        List<ViewIngresosPorMes> resultado;

        List<ViewIngresosPorMes> ingresos = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewIngresosPorMes.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(ingresos);

        resultado = flujoDeCajaService.verIngresosAnuales();


        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void SiInvocoVerEgresosAnualesYExistenEgresosRetornaListaDeEgresos(){
        List<ViewEgresosPorMes> resultado;

        List<ViewEgresosPorMes> egresos = getEgresos();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewEgresosPorMes.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(egresos);

        resultado = flujoDeCajaService.verEgresosAnuales();


        assertNotNull(resultado);
        assertEquals(egresos.get(0),resultado.get(0));
    }

    @Test
    void SiInvocoVerEgresosAnualesYNoExistenEgresosRetornaListaVacia(){
        List<ViewEgresosPorMes> resultado;

        List<ViewEgresosPorMes> egresos = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewEgresosPorMes.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(egresos);

        resultado = flujoDeCajaService.verEgresosAnuales();


        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void siInvocoVerFlujoCajaYExisteUnFlujoDebeRetornarListaFlujoView(){
        List<ViewFlujoDeCaja> resultado;

        List<ViewFlujoDeCaja> flujos = getFlujoDeCaja();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewFlujoDeCaja.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(flujos);

        resultado = flujoDeCajaService.verFlujoDeCaja();


        assertNotNull(resultado);
        assertEquals(flujos.get(0),resultado.get(0));
    }

    @Test
    void siInvocoVerFlujoCajaYNoExisteUnFlujoDebeRetornarListaVacia(){
        List<ViewFlujoDeCaja> resultado;

        List<ViewFlujoDeCaja> flujos = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewFlujoDeCaja.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(flujos);

        resultado = flujoDeCajaService.verFlujoDeCaja();


        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    List<ViewIngresosPorMes> getIngresos(){
        List<ViewIngresosPorMes> ingresosPorMes= new ArrayList<>();

        ViewIngresosPorMes viewIngresosPorMes = new ViewIngresosPorMes();
        viewIngresosPorMes.setIngresos(15000);
        viewIngresosPorMes.setMes("Enero");
        ingresosPorMes.add(viewIngresosPorMes);

        viewIngresosPorMes.setIngresos(10000);
        viewIngresosPorMes.setMes("Febrero");
        ingresosPorMes.add(viewIngresosPorMes);

        viewIngresosPorMes.setIngresos(23999);
        viewIngresosPorMes.setMes("Mayo");
        ingresosPorMes.add(viewIngresosPorMes);

        viewIngresosPorMes.setIngresos(34000);
        viewIngresosPorMes.setMes("Diciembre");
        ingresosPorMes.add(viewIngresosPorMes);

        return ingresosPorMes;
    }

    List<ViewEgresosPorMes> getEgresos(){
        List<ViewEgresosPorMes> egresosPorMes= new ArrayList<>();

        ViewEgresosPorMes viewEgresosPorMes = new ViewEgresosPorMes();

        viewEgresosPorMes.setEgresos(5000);
        viewEgresosPorMes.setMes("Enero");
        egresosPorMes.add(viewEgresosPorMes);

        viewEgresosPorMes.setEgresos(2000);
        viewEgresosPorMes.setMes("Marzo");
        egresosPorMes.add(viewEgresosPorMes);

        viewEgresosPorMes.setEgresos(2999);
        viewEgresosPorMes.setMes("Julio");
        egresosPorMes.add(viewEgresosPorMes);

        viewEgresosPorMes.setEgresos(10000);
        viewEgresosPorMes.setMes("Diciembre");
        egresosPorMes.add(viewEgresosPorMes);

        return egresosPorMes;
    }

    List<ViewFlujoDeCaja> getFlujoDeCaja(){
        List<ViewFlujoDeCaja> flujos = new ArrayList<>();
        List<ViewEgresosPorMes> egresos= getEgresos();
        List<ViewIngresosPorMes> ingresos = getIngresos();


        ViewFlujoDeCaja flujo = new ViewFlujoDeCaja();

        flujo.setMes("Enero");
        flujo.setIngresos(ingresos.get(0).getIngresos());
        flujo.setEgresos(egresos.get(0).getEgresos());
        flujos.add(flujo);

        flujo.setMes("Febrero");
        flujo.setIngresos(ingresos.get(1).getIngresos());
        flujos.add(flujo);

        flujo.setMes("Marzo");
        flujo.setEgresos(egresos.get(1).getEgresos());
        flujos.add(flujo);

        flujo.setMes("Mayo");
        flujo.setIngresos(ingresos.get(2).getIngresos());
        flujos.add(flujo);

        flujo.setMes("Julio");
        flujo.setEgresos(egresos.get(2).getEgresos());
        flujos.add(flujo);

        flujo.setMes("Diciembre");
        flujo.setIngresos(ingresos.get(3).getIngresos());
        flujo.setEgresos(egresos.get(3).getEgresos());
        flujos.add(flujo);

        return  flujos;
    }
}
