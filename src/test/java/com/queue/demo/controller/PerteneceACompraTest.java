package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.*;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PerteneceACompraTest {
    private JacksonTester<PerteneceACompra> jsonPerteneceACompra;
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
    void siInvocoBuscarTodosLosPerteneceACompraDebeRetornarPerteneceACompraList() throws Exception {
        IdPerteneceACompra idPerteneceACompra1;
        idPerteneceACompra1 = new IdPerteneceACompra();
        idPerteneceACompra1.setIdcompra(1);
        idPerteneceACompra1.setIdproducto(1);
        IdPerteneceACompra idPerteneceACompra2;
        idPerteneceACompra2 = new IdPerteneceACompra();
        idPerteneceACompra2.setIdcompra(2);
        idPerteneceACompra2.setIdproducto(2);
        Producto producto1;
        producto1=new Producto();
        producto1.setIdproducto(1);
        producto1.setStock(10);
        producto1.setStockmin(2);
        producto1.setValorcompra(100);
        producto1.setValorventa(1000);
        producto1.setDetalleproducto("si");
        Producto producto2;
        producto2=new Producto();
        producto2.setIdproducto(2);
        producto2.setStock(10);
        producto2.setStockmin(2);
        producto2.setValorcompra(100);
        producto2.setValorventa(1000);
        producto2.setDetalleproducto("si");
        Compra compra1;
        compra1=new Compra();
        compra1.setIdcompra(1);
        compra1.setRutempresa("11111111-1");
        compra1.setRutusuario("12345678-9");
        Compra compra2;
        compra2=new Compra();
        compra2.setIdcompra(2);
        compra2.setRutempresa("22222222-2");
        compra2.setRutusuario("12345677-7");
        PerteneceACompra perteneceACompra1 = new PerteneceACompra(idPerteneceACompra1,producto1,compra1,1);
        PerteneceACompra perteneceACompra2 = new PerteneceACompra(idPerteneceACompra2,producto1,compra1,2);
        List<PerteneceACompra> perteneceACompra = new  ArrayList<>();
        perteneceACompra.add(perteneceACompra1);
        perteneceACompra.add(perteneceACompra2);
        given(perteneceACompraService.ListarTodasLasCompras()).willReturn(perteneceACompra);

        this.mockMvc.perform(get("/pertenececompra"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(perteneceACompra.size()));
    }
}
