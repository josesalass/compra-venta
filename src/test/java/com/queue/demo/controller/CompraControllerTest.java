package com.queue.demo.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Compra;
import com.queue.demo.model.Usuario;
import com.queue.demo.model.Venta;
import com.queue.demo.service.AuthException;
import com.queue.demo.service.CompraException;
import com.queue.demo.service.CompraService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CompraControllerTest {

    private JacksonTester<Compra> jsompra;
    private MockMvc mockMvc;
    @Mock
    private CompraServiceImpl compraService;
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
        given(compraService.buscarTodasLasCompras()).willReturn(compras);
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
        given(compraService.buscarTodasLasCompras()).willReturn(compras);

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
        given(compraService.guardarCompra(any(Compra.class))).willReturn(compra);

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
        doThrow(CompraException.class).when(compraService).guardarCompra(any(Compra.class));

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
    void siInvocoGuardarCompraYElUsuarioNoEstaAutorizadoDebeRetornarStatusUnauthorized() throws Exception {

        // Given
        Compra compra = getCompra();
        doThrow(AuthException.class).when(compraService).guardarCompra(any(Compra.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/compras/guardarCompra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsompra.write(compra).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED.value(),response.getStatus());
    }

    @Test
    void siInvocoCambioFechaPorIdSeDebeCambiarLaFechaDeLaCompraYDevolverElStatusOK() throws Exception {
        // Given
        Compra compra = getCompra();
        Compra compraf= compra;
        compraf.setFecha(Timestamp.valueOf("2020-11-23 00:00:00"));
        given(compraService.buscarCompraPorId(compra.getIdcompra())).willReturn(Optional.of(compra));
        given(compraService.actualizarCompra(compra.getIdcompra(),compra)).willReturn(compra);

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/compras/{idcompra}/cambiarFecha/{fecha}",compra.getIdcompra(),"2020-11-23 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    //editarFecha Unauthorized
    @Test
    void siInvocoCambioFechaPorIdYNoSePuedeCambiarLaFechaDeLaCompraDebeDevolverElStatusUnauthorized() throws Exception{
        //Given
        Compra compra = getCompra();
        Usuario usuario=getUsuario();
        given(compraService.buscarCompraPorId(compra.getIdcompra())).willReturn(Optional.of(compra));
        doThrow(AuthException.class).when(compraService).actualizarCompra(eq(compra.getIdcompra()),any(Compra.class));
        MockHttpServletResponse response = mockMvc.perform(put("/compras/{idcompra}/cambiarFecha/{fecha}",compra.getIdcompra(),Timestamp.valueOf("2005-10-30 00:00:00"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.UNAUTHORIZED.value(),response.getStatus());
    }

    @Test
    void siInvocoCambioFechaPorIdYNoSePuedeCambiarLaFechaDeLaCompraDebeDevolverElStatusBadRequest() throws Exception {
        // Given
        Compra compra = getCompra();
        String fechan= "lo que sea";
        given(compraService.buscarCompraPorId(compra.getIdcompra())).willReturn(Optional.of(compra));


        // When
        mockMvc.perform(put("/compras/{idcompra}/cambiarFecha/{fecha}",compra.getIdcompra(),fechan)).andExpect(status().isBadRequest());

    }
    @Test
    void siInvocoCambioFechaYLaCompraNoExisteDebeDevolverElStatusBadRequest() throws Exception{
        Compra compra = getCompra();
        given(compraService.buscarCompraPorId(compra.getIdcompra())).willReturn(Optional.empty());

        mockMvc.perform(put("/compras/{idcompra}/cambiarFecha/{fecha}",compra.getIdcompra(),"2020-11-23 00:00:00")).andExpect(status().isBadRequest());
    }

    @Test
    void siInvocoCambiarRutEmpresaPorIdSeDebeCambiarLaFechaDeLaCompraYDevolverElStatusOK() throws Exception {
        // Given
        Compra compra = getCompra();
        Compra compraf= compra;
        compraf.setRutempresa("792954367");
        given(compraService.buscarCompraPorId(compra.getIdcompra())).willReturn(Optional.of(compra));
        given(compraService.actualizarCompra(compra.getIdcompra(),compra)).willReturn(compraf);

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
    void siInvocoCambiarRutEmpresaPorIdYNoSePuedeCambiarElRutEmpresaDeLaCompraDebeDevolverElStatusUnauthorized() throws Exception {

        Compra compra = getCompra();
        Usuario usuario=getUsuario();
        given(compraService.buscarCompraPorId(compra.getIdcompra())).willReturn(Optional.of(compra));
        doThrow(AuthException.class).when(compraService).actualizarCompra(eq(compra.getIdcompra()),any(Compra.class));

        mockMvc.perform(put("/compras/{idcompra}/cambiarEmpresa/{rutempresa}",compra.getIdcompra(),"792954367")).andExpect(status().isUnauthorized());

    }

    @Test
    void siInvocoCambiarRutEmpresaPorIdYNoSePuedeCambiarElRutEmpresaDeLaCompraDebeDevolverElStatusBadRequest() throws Exception {

        Compra compra = getCompra();
        given(compraService.buscarCompraPorId(compra.getIdcompra())).willReturn(Optional.empty());

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
    private Usuario getUsuario() {
        Usuario usuario = new Usuario("123576642","jose","apellido1","tres","ada@gmail.com",Usuario.ADMIN_COMPRAS,"producto1");
        return  usuario;
    }




}
