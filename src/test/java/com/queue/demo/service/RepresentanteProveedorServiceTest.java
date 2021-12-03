package com.queue.demo.service;


import com.queue.demo.model.*;
import com.queue.demo.repository.RepositorioAsociada_Venta;
import com.queue.demo.repository.RepositorioRepresentanteProveedor;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RepresentanteProveedorServiceTest {

    @Mock
    private RepositorioRepresentanteProveedor repositorioRepresentanteProveedor;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery criteriaQuery=mock(CriteriaQuery.class);

    @Mock
    private CriteriaQuery criteriaQueryAll=mock(CriteriaQuery.class);

    @Mock
    private Root<RepresentanteProveedor> root;

    @Mock
    private TypedQuery typedQuery=mock(TypedQuery.class);

    @InjectMocks
    private RepresentanteProveedorServiceImpl representanteProveedorService;

    @Test
    void siInvocoListarTodosLosRepProveedorYExistenRepProveedorListaDebeRetornarUnaListaConLosRepProveedor(){
        //Arrage
        List<RepresentanteProveedor> resultado;
        List<RepresentanteProveedor> repProveedor= getRepProveedor();
        when(representanteProveedorService.buscarTodosLosRepresentanteProveedor()).thenReturn(repProveedor);
        //Act
        resultado= representanteProveedorService.buscarTodosLosRepresentanteProveedor();
        //Assert
        assertNotNull(resultado);
        assertEquals(repProveedor.size(),resultado.size());
    }
    @Test
    void siInvocoListarTodosLosRepProveedorYNoExistenDebeRetornarUnaListaUnaListaVacia(){
        //Arrage
        List<RepresentanteProveedor> resultado;
        //Act
        resultado= representanteProveedorService.buscarTodosLosRepresentanteProveedor();
        //Act
        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }

/*
    @Test
    void siInvocoBuscarRepProveedorPorRutYExisteElRepProveedorDebeRetornarElRepProveedor(){

    }
*/

    @Test
    void siInvocoSaveAsociadaVentaYSeGuardaSatisfactoriamente() throws Exception{
        //Arrage
        RepresentanteProveedor repProveedor= getRepProveedor().get(0);
        given(representanteProveedorService.guardar(repProveedor)).willAnswer(invocation -> invocation.getArgument(0));
        //Act
        RepresentanteProveedor repProveedorf = representanteProveedorService.guardar(repProveedor);
        //Assert
        assertThat(repProveedorf).isNotNull();
        verify(repositorioRepresentanteProveedor).save(any(RepresentanteProveedor.class));
    }

    @Test
    void SiInvocoSaveAsociadaVentaConAsociadaVentaNullDebeRetornarException() {
        // Arrange + Act
        RepresentanteProveedor repProveedor= null;
        // Assert
        assertThrows(Exception.class,() -> {
            representanteProveedorService.guardar(repProveedor);
        });

        verify(repositorioRepresentanteProveedor, never()).save(any(RepresentanteProveedor.class));
    }
    @Test
    void SiInvodoBorrarRepProveedorPorIdDebeEliminarElRepProveedor() {
        // Arrange + Act
        String rutrep= getRepProveedor().get(0).getRutrep();
        representanteProveedorService.eliminarRepresentanteProveedorPorRut(rutrep);
        representanteProveedorService.eliminarRepresentanteProveedorPorRut(rutrep);

        // Assert
        verify(repositorioRepresentanteProveedor, times(2)).deleteByrutrep(rutrep);
    }



    @Test
     void siInvocoBuscarRepProveedorPorRutYEncuentraUnRepProveedorDebeRetornarlo() throws Exception {
        Optional <RepresentanteProveedor> respuesta;

        List<RepresentanteProveedor> mocklist = new ArrayList<>();
        mocklist.add(getRepProveedor().get(0));

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(RepresentanteProveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutrep"), getRepProveedor().get(0).getRutrep()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);


        respuesta=representanteProveedorService.buscarRepresentantePorRut(getRepProveedor().get(0).getRutrep());

        assertNotNull(respuesta);
        assertEquals(respuesta.get().getRutrep(),getRepProveedor().get(0).getRutrep());

    }

    @Test
    void siInvocoBuscarRepProveedorPorRutYNoEncuentraUnRepProveedorDebeRetornarnull() throws Exception {
        Optional <RepresentanteProveedor> respuesta;

        List<RepresentanteProveedor> mocklist = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(RepresentanteProveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutrep"), null))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);


        respuesta=representanteProveedorService.buscarRepresentantePorRut(getRepProveedor().get(0).getRutrep());

        assertNotNull(respuesta);
        assertTrue(respuesta.isEmpty());

    }


    // datos para los test

    private List<RepresentanteProveedor> getRepProveedor(){
        List<RepresentanteProveedor> list= new ArrayList<>();
        RepresentanteProveedor rep= new RepresentanteProveedor();
        rep.setApellido1("");
        rep.setApellido2("");
        rep.setCorreo("");
        rep.setEstado(true);
        rep.setNombre("");
        rep.setRutemp("");
        rep.setRutrep("12345");

        list.add(rep);
        return list;
    }


}
