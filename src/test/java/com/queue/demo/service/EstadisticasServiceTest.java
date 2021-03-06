package com.queue.demo.service;

import com.queue.demo.model.ViewProductoMasVendidoPorMes;
import com.queue.demo.model.ViewProductoMenosVendidoPorMes;
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

    @Test
    void siInvocoVerProductoMenosVendidoDebeRetornarViewProductoMenosVendidoList(){
        List<ViewProductoMenosVendidoPorMes> resultado;
        List<ViewProductoMenosVendidoPorMes> mocklist = getViewVerminimo();
        String comparar = "2021%";

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewProductoMenosVendidoPorMes.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.like(root.get("fecha"),comparar))).thenReturn(criteriaQuery);


        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        resultado = estadisticasService.verProductoMenosVendido(2021);

        assertNotNull(resultado);
        assertEquals(resultado.get(0),mocklist.get(0));
    }

    @Test
    void siInvocoVerProductoMasVendidoDebeRetornarViewProductoMasVendidoList(){
        List<ViewProductoMasVendidoPorMes> resultado;
        List<ViewProductoMasVendidoPorMes> mocklist = getProductoMasVendidoPorMes();
        String comparar = "2021%";

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewProductoMasVendidoPorMes.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.like(root.get("fecha"),comparar))).thenReturn(criteriaQuery);


        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        resultado = estadisticasService.verProductoMasVendido(2021);

        assertNotNull(resultado);
        assertEquals(resultado.get(0),mocklist.get(0));
    }

    private List<ViewPromedioVentasMes> getViewPromedioVentasMes() {
        List<ViewPromedioVentasMes> p = new ArrayList<>();
        ViewPromedioVentasMes v = new ViewPromedioVentasMes();
        v.setAnio_mes("2021-01");
        v.setPromedio_mensual(3000);
        p.add(v);
        v.setAnio_mes("2021-02");
        v.setPromedio_mensual(1000);
        p.add(v);
        return p;
    }
    private List<ViewProductoMenosVendidoPorMes> getViewVerminimo() {
        List<ViewProductoMenosVendidoPorMes> p = new ArrayList<>();
        ViewProductoMenosVendidoPorMes v = new ViewProductoMenosVendidoPorMes();
        v.setFecha("2021-01");
        v.setCantidad(30000);
        v.setDetalleproducto("ahjgdsb");
        p.add(v);
        v.setFecha("2021-02");
        v.setCantidad(1000);
        v.setDetalleproducto("ajshdkjasbfkhasb");
        p.add(v);
        return p;
    }
    private List<ViewProductoMasVendidoPorMes> getProductoMasVendidoPorMes() {
        List<ViewProductoMasVendidoPorMes> p= new ArrayList<>();
        ViewProductoMasVendidoPorMes v= new ViewProductoMasVendidoPorMes();
        v.setCantidad(120);
        v.setFecha("2021-09");
        v.setDetalleproducto("Harina");
        p.add(v);
        return p;
    }


}
