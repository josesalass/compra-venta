package com.queue.demo;


import com.queue.demo.model.Compra;
import com.queue.demo.repository.RepositorioCompra;
import com.queue.demo.service.CompraService;
import com.queue.demo.service.CompraServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock
    private RepositorioCompra repCompra;

    @InjectMocks
    private CompraServiceImpl compraServiceImpl;

    @Test
    void siInvocoBuscarTodasLasComprasDebeRetornarListCompra() {
        // Arrange
        List<Compra> resultado;
        List<Compra> compra = getListaCompra();
        when(repCompra.findAll()).thenReturn(compra);

        // Act
        resultado = compraServiceImpl.buscarTodasLasCompras();

        // Assert
        assertNotNull(resultado);
        assertEquals(compra.size(),resultado.size());
        assertEquals(compra.get(0).getIdcompra(),resultado.get(0).getIdcompra());
    }

    // Metodos que generan datos de prueba
    private List<Compra> getListaCompra() {


        List<Compra> compras = new ArrayList<>();
        Compra compra = new Compra();
        compra.setIdcompra(1);
        compra.setRutusuario("1");
        compras.add(compra);
        return compras;
    }
}
