package com.queue.demo.service;


import com.queue.demo.model.*;
import com.queue.demo.repository.RepositorioAsociada_Venta;
import com.queue.demo.repository.RepositorioPerteneceACompra;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PerteneceACompraServiceTest {

    @Mock
    RepositorioPerteneceACompra repositorioPerteneceACompra;

    @InjectMocks
    PerteneceACompraServiceImpl perteneceACompraService;


    @Test
    void siInvocoListarTodasLasComprasYExistenComprasDebeRetornarUnaListaConLasCompras(){
        //Arrage
        List<PerteneceACompra> resultado;
        List<PerteneceACompra> compras= getPerteneceACompra();
        when(perteneceACompraService.ListarTodasLasCompras()).thenReturn(compras);
        //Act
        resultado= perteneceACompraService.ListarTodasLasCompras();
        //Assert
        assertNotNull(resultado);
        assertEquals(compras.size(),resultado.size());
    }
    @Test
    void siInvocoListarTodasLasComprasYnoExistenComprasDebeRetornarUnaListaVacia(){
        //Arrage
        List<PerteneceACompra> resultado;
        //Act
        resultado= perteneceACompraService.ListarTodasLasCompras();
        //Act
        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }


    @Test
    void siInvocoSavePerteneceACompraYSeGuardaSatisfactoriamente() throws Exception{
        //Arrage
        PerteneceACompra perteneceACompra= getPerteneceACompra().get(0);
        given(perteneceACompraService.guardarPerteneceACompra(perteneceACompra)).willAnswer(invocation -> invocation.getArgument(0));
        //Act
        PerteneceACompra compraf = perteneceACompraService.guardarPerteneceACompra(perteneceACompra);
        //Assert
        assertThat(compraf).isNotNull();
        verify(repositorioPerteneceACompra).save(any(PerteneceACompra.class));
    }

    @Test
    void SiInvocoSavePerteneceCompraConPerteneceCompraNullDebeRetornarException() {
        // Arrange + Act
        PerteneceACompra perteneceACompra= null;
        // Assert
        assertThrows(Exception.class,() -> {
            perteneceACompraService.guardarPerteneceACompra(perteneceACompra);
        });

        verify(repositorioPerteneceACompra, never()).save(any(PerteneceACompra.class));
    }


    // datos para los test

    private List<PerteneceACompra> getPerteneceACompra(){

        List<PerteneceACompra> list= new ArrayList<>();
        Producto p= new Producto(1,10,5,560,700,"Awa");
        Compra c= new Compra();
        IdPerteneceACompra id= new IdPerteneceACompra();
        id.setIdcompra(c.getIdcompra());
        id.setIdproducto(p.getIdproducto());
        PerteneceACompra a= new PerteneceACompra(id,p,c,12);
        /*a.setCompra(c);
        a.setIdPerteneceACompra(id);
        a.setProducto(p);
        a.setCantidad(12);
        */
        list.add(a);
        return list;
    }

}
