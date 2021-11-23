package com.queue.demo.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.controller.CompraController;
import com.queue.demo.model.Compra;
import com.queue.demo.service.CompraException;
import com.queue.demo.service.CompraServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CompraControllerTest {

    private JacksonTester<Compra> jsompra;
    private MockMvc mockMvc;
    @Mock
    private CompraServiceImpl compraServiceimp;
    @InjectMocks
    private CompraController  compraController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(compraController).build();
    }
    @Test
    void SiInvocoFuncionListDebeRetornarTodosLasCompras() throws Exception {
        // Given
        List<Compra> compras = getCompras();
        given(compraServiceimp.buscarTodasLasCompras()).willReturn(compras);
        // When
        MockHttpServletResponse response= mockMvc.perform(get("/compras")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(),response.getStatus());

    }
    @Test
    void SiInvocoFuncionListYNoExistenComprasDebeRetornarListaVacia() throws Exception {
        // Given
        List<Compra> compras = new ArrayList<>();
        given(compraServiceimp.buscarTodasLasCompras()).willReturn(compras);

        MockHttpServletResponse response = mockMvc.perform(get("/compras")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

         assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());

    }

    @Test
    void siInvocoGuardarCompraSeDebeAlmacenarYDevolverElStatusCREATED() throws Exception {
        // Given
        Compra compra = getCompra();
        given(compraServiceimp.guardarCompra(any(Compra.class))).willReturn(compra);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/compras/guardarCompra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsompra.write(compra).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
    }

    @Test
    void siInvocoGuardarCompraYNoSePuedeCrearSeDebeDevolverElStatusBadRequest() throws Exception {

        // Given
        Compra compra = getCompra();
        doThrow(CompraException.class).when(compraServiceimp).guardarCompra(any(Compra.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/compras/guardarCompra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsompra.write(compra).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }
    @Test
    void siInvocoCambioFechaPorIdSeDebeCambiarLaFechaDeLaCompraYDevolverElStatusOK() throws Exception {
        // Given
        Compra compra = getCompra();
        Compra compraf= compra;
        compraf.setFecha(Timestamp.valueOf("2020-11-23 00:00:00"));
        given(compraServiceimp.buscarCompraPorId(compra.getIdcompra())).willReturn(compra);
        given(compraServiceimp.actualizarCompra(compra.getIdcompra(),compra)).willReturn(compraf);

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/compras/{idcompra}/cambiarFecha/{fecha}",compra.getIdcompra(),"2020-11-23 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    void siInvocoCambioFechaPorIdYNoSePuedeCambiarLaFechaDeLaCompraDebeDevolverElStatusBadRequest() throws Exception {
        // Given
        Compra compra = getCompra();
        String fechan= "lo que sea";
        given(compraServiceimp.buscarCompraPorId(compra.getIdcompra())).willReturn(compra);


        // When
        mockMvc.perform(put("/compras/{idcompra}/cambiarFecha/{fecha}",compra.getIdcompra(),fechan)).andExpect(status().isBadRequest());

    }
    @Test
    void siInvocoCambioFechaYLaCompraNoExisteDebeDevolverElStatusBadRequest() throws Exception{
        Compra compra = getCompra();
        given(compraServiceimp.buscarCompraPorId(compra.getIdcompra())).willReturn(null);

        mockMvc.perform(put("/compras/{idcompra}/cambiarFecha/{fecha}",compra.getIdcompra(),"2020-11-23 00:00:00")).andExpect(status().isBadRequest());
    }

    @Test
    void siInvocoCambiarRutEmpresaPorIdSeDebeCambiarLaFechaDeLaCompraYDevolverElStatusOK() throws Exception {
        // Given
        Compra compra = getCompra();
        Compra compraf= compra;
        compraf.setRutempresa("792954367");
        given(compraServiceimp.buscarCompraPorId(compra.getIdcompra())).willReturn(compra);
        given(compraServiceimp.actualizarCompra(compra.getIdcompra(),compra)).willReturn(compraf);

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/compras/{idcompra}/cambiarEmpresa/{rutempresa}",compra.getIdcompra(),"792954367")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoCambiarRutEmpresaPorIdYNoSePuedeCambiarElRutEmpresaDeLaCompraDebeDevolverElStatusBadRequest() throws Exception {

        Compra compra = getCompra();
        given(compraServiceimp.buscarCompraPorId(compra.getIdcompra())).willReturn(null);

        mockMvc.perform(put("/compras/{idcompra}/cambiarEmpresa/{rutempresa}",compra.getIdcompra(),"792954367")).andExpect(status().isBadRequest());

    }





    //Datos para los test
    List<Compra> getCompras() {
        List<Compra>compras = new ArrayList<Compra> ();
        Compra compra= new Compra();
        compra.setRutusuario("123576642");
        compra.setFecha(new Timestamp(System.currentTimeMillis()));
        compra.setRutempresa("257963345");
        compras.add(compra);
        return compras;
    }

    private Compra getCompra(){
        Compra compra= new Compra();
        compra.setRutusuario("123576642");
        compra.setFecha(new Timestamp(System.currentTimeMillis()));
        compra.setRutempresa("257963345");
        return compra;
    }




}
