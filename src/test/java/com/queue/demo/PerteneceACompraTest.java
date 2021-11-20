package com.queue.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.controller.PerteneceACompraController;
import com.queue.demo.model.*;
import com.queue.demo.repository.RepositorioPerteneceACompra;
import com.queue.demo.service.PerteneceACompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class PerteneceACompraTest {
    private JacksonTester<PerteneceACompra> jsonEmpleado;
    private MockMvc mockMvc;
    @Mock
    private PerteneceACompraService perteneceACompraService;
    @InjectMocks
    private PerteneceACompraController perteneceACompraController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(perteneceACompraController).build();
    }

    @Test
    void siInvocoAddCompraYFunca()throws Exception{
        //Given
        PerteneceACompra perteneceACompra=getPerteneceACompra();
        given(perteneceACompraService.guardarPerteneceACompra(any(PerteneceACompra.class))).willReturn(perteneceACompra);

        //When
        MockHttpServletResponse response = mockMvc.perform(post("/pertenececompra/guardarpcompra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmpleado.write(perteneceACompra).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //Then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals(jsonEmpleado.write(perteneceACompra).getJson(),response.getContentAsString());

        }
    @Test
    void siInvocoCreateUsuarioYNoFunca() throws Exception {
        // Given
        PerteneceACompra perteneceACompra = getPerteneceACompra();
        doThrow(Exception.class).when(perteneceACompraService).guardarPerteneceACompra(any(PerteneceACompra.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/pertenececompra/guardarpcompra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmpleado.write(perteneceACompra).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }
    PerteneceACompra getPerteneceACompra() {
        PerteneceACompra perteneceACompra = new PerteneceACompra();
        IdPerteneceACompra idPerteneceACompra;
        idPerteneceACompra = new IdPerteneceACompra();
        idPerteneceACompra.setIdcompra(1);
        idPerteneceACompra.setIdproducto(1);
        perteneceACompra.setIdPerteneceACompra(idPerteneceACompra);
        Producto producto;
        producto=new Producto();
        producto.setIdproducto(1);
        producto.setStock(10);
        producto.setStockmin(2);
        producto.setValorcompra(100);
        producto.setValorventa(1000);
        producto.setDetalleproducto("si");
        perteneceACompra.setProducto(producto);
        Compra compra;
        compra=new Compra();
        compra.setIdcompra(1);
        //compra.setFecha();
        compra.setRutempresa("11111111-1");
        compra.setRutusuario("12345678-9");
        perteneceACompra.setCompra(compra);
        perteneceACompra.setCantidad(1);
        return perteneceACompra;
    }
}
