package com.queue.demo.service;

import com.queue.demo.model.Compra;
import com.queue.demo.repository.RepositorioCompra;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {
    @Mock
    private RepositorioCompra repositorioCompra;
    @InjectMocks
    private CompraServiceImpl compraService;

    //buscarTodasLasCompras caso exitoso
    @Test
    void siInvocoBuscarTodasLasComprasDebeRetornarCompraList(){
        List<Compra> resultado;
        List<Compra> compras = getCompras();
        when(repositorioCompra.findAll()).thenReturn(compras);

        resultado = compraService.buscarTodasLasCompras();

        assertNotNull(resultado);
        assertEquals(compras.size(),resultado.size());
        assertEquals(compras.get(0).getIdcompra(),resultado.get(0).getIdcompra());
        assertEquals(compras.get(1).getIdcompra(),resultado.get(1).getIdcompra());
    }

    //buscarTodasLasCompras caso no existen compras
    @Test
    void siInvocoBuscarTodasLasComprasYNoExistenComprasDebeRetornarCompraListVacia(){
        List<Compra> resultado;
        List<Compra> compras = new ArrayList<>();
        when(repositorioCompra.findAll()).thenReturn(compras);

        resultado = compraService.buscarTodasLasCompras();

        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }

    //buscarCompraPorId caso exitoso
    @Test
    void siInvocoBuscarCompraPorIdYExisteUnaCompraConEseIdDebeRetornarCompra(){
        Optional<Compra> resultado;
        Compra compra = getCompras().get(0);
        when(repositorioCompra.findById(1)).thenReturn(Optional.of(compra));

        resultado = compraService.buscarCompraPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
        assertEquals(compra.getRutempresa(),resultado.get().getRutempresa());
    }

    //buscarCompraPorId caso no encuentra compra
    @Test
    void siInvocoBuscarCompraPorIdYNoExisteUnaCompraConEseIdDebeRetornarOptionalVacio(){
        Optional<Compra> resultado;
        when(repositorioCompra.findById(1)).thenReturn(Optional.empty());

        resultado = compraService.buscarCompraPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    //borrarCompraPorId caso exitoso
    @Test
    void siInvocoBorrarCompraPorIdYExisteLaCompraDebeBorrarlaYRetornarTrue(){
        Compra compra = getCompras().get(0);
        boolean resultado;
        when(repositorioCompra.getById(1)).thenReturn(compra);

        resultado = compraService.borrarCompraPorId(1);

        assertTrue(resultado);
    }

    //borrarCompraPorId caso no encuentra la compra
    @Test
    void siInvocoBorrarCompraPorIdYNoExisteLaCompraDebeRetornarFalse(){
        boolean resultado;
        doThrow(EntityNotFoundException.class).when(repositorioCompra).getById(1);

        resultado = compraService.borrarCompraPorId(1);

        assertNotNull(resultado);
        assertFalse(resultado);
    }



    private List<Compra> getCompras() {
        List<Compra> compras = new ArrayList<>();
        Compra compra = new Compra();
        compra.setIdcompra(1);
        compra.setRutusuario("12");
        compra.setRutempresa("21");
        compras.add(compra);

        compra.setIdcompra(2);
        compra.setRutusuario("34");
        compra.setRutempresa("43");
        compras.add(compra);

        return compras;
    }
}
