package com.queue.demo.service;

import com.queue.demo.model.Asociada_Venta;
import com.queue.demo.model.PerteneceACompra;
import com.queue.demo.model.Producto;
import com.queue.demo.model.Venta;
import com.queue.demo.repository.RepositorioAsociada_Venta;
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
public class Asociada_VentaServiceTest {


    @Mock
    private RepositorioAsociada_Venta repositorioAsociada_venta;

    @InjectMocks
    private Asociada_VentaServiceImpl asociada_ventaService;

    @Test
    void siInvocoBuscarTodasLasVentasAsociadasYExistenVentasAsociadasDebeRetornarUnaListaConLasVentasAsociadas(){
        //Arrage
        List<Asociada_Venta> resultado;
        List<Asociada_Venta> ventas= getAsociadaVenta();
        when(asociada_ventaService.buscarTodasLasVentasAsociadas()).thenReturn(ventas);
        //Act
        resultado= asociada_ventaService.buscarTodasLasVentasAsociadas();
        //Assert
        assertNotNull(resultado);
        assertEquals(ventas.size(),resultado.size());
    }
    @Test
    void siInvocoBuscarTodasLasVentasAsociadasYnoExistenVentasAsociadasDebeRetornarUnaListaVacia(){
        //Arrage
        List<Asociada_Venta> resultado;
        //Act
        resultado= asociada_ventaService.buscarTodasLasVentasAsociadas();
        //Act
        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }

    @Test
    void siInvocoSaveAsociadaVentaYSeGuardaSatisfactoriamente() throws Exception{
        //Arrage
        Asociada_Venta asociada_venta= getAsociadaVenta().get(0);
        given(asociada_ventaService.saveAsociadaVenta(asociada_venta)).willAnswer(invocation -> invocation.getArgument(0));
        //Act
        Asociada_Venta asoGuardado = asociada_ventaService.saveAsociadaVenta(asociada_venta);
        //Assert
        assertThat(asoGuardado).isNotNull();
        verify(repositorioAsociada_venta).save(any(Asociada_Venta.class));
    }

    @Test
    void SiInvocoSaveAsociadaVentaConAsociadaVentaNullDebeRetornarException() {
        // Arrange + Act
        Asociada_Venta asociada_venta= null;
        // Assert
        assertThrows(Exception.class,() -> {
            asociada_ventaService.saveAsociadaVenta(asociada_venta);
        });

        verify(repositorioAsociada_venta, never()).save(any(Asociada_Venta.class));
    }




    // datos para los test

    private List<Asociada_Venta> getAsociadaVenta(){
        List<Asociada_Venta> list= new ArrayList<>();
        Producto p= new Producto(1,10,5,560,700,"Awa");
        Venta v= new Venta();
        Asociada_Venta a= new Asociada_Venta(p,v,15);
        list.add(a);
        return list;
    }
}
