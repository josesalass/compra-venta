package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Venta;
import com.queue.demo.repository.RepositorioVenta;
import com.queue.demo.service.VentaService;
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
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class VentaControllerTest {
    private JacksonTester<Venta> jsonVenta;
    private MockMvc mockMvc;
    @Mock
    private VentaService ventaService;
    @InjectMocks
    private VentaController ventaController;

    @BeforeEach
    void setup(){
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(ventaController).build();
    }

    //saveVenta exitoso
    @Test
    void siInvocoSaveVentaDebeAlmacenarYDevolverLaVentaConStatusCreated() throws Exception{
        //Given
        Venta venta = getVenta();
        given(ventaService.guardarVenta(any(Venta.class))).willReturn(venta);

        //When
        MockHttpServletResponse response = mockMvc.perform(post("/ventas/guardarVenta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVenta.write(venta).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals(jsonVenta.write(venta).getJson(),response.getContentAsString());
    }
    /*
    //saveVenta bad request
    @Test
    void siInvocoSaveVentaSeDebeDevolverElStatusBadRequest() throws Exception{
        //Given
        Venta venta = getVenta();
        doThrow(Exception.class).when(ventaService).guardarVenta(any(Venta.class));

        //When
        MockHttpServletResponse response = mockMvc.perform(post("/ventas/guardarVenta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonVenta.write(venta).getJson())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());

    }
    */
    /*
    //editarFecha caso exitoso
    @Test
    void siInvocoEditarFechaYNoHayNulosDebeDevolverStatusOk() throws Exception{
        //Given
        Venta venta = getVenta();
        given(ventaService.editarFecha(Timestamp.valueOf("2005-10-30 00:00:00"),venta.getIdventa())).willReturn(true);

        //When
        MockHttpServletResponse response = mockMvc.perform(put("/editFecha?idventa=1&fecha=2005-10-30 00:00:00")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());

    }

    //editarFecha caso bad request
    @Test
    void siInvocoEditarFechaYHayNulosDebeDevolverStatusBadRequest() throws Exception{
        //Given
        Venta venta = getVenta();
        doThrow(NullPointerException.class).when(ventaService).editarFecha(Timestamp.valueOf("2005-10-30 00:00:00"),1);
        //When
        MockHttpServletResponse response = mockMvc.perform(put("/editFecha?idventa=1&fecha=2005-10-30 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());

    }
    */
    /*
    //editarTipo caso OK
    @Test
    void siInvocoEditarTipoYNoHayNulosDebeDevolverStatusOk() throws Exception{
        //Given
        Venta venta = getVenta();
        given(ventaService.editarTipo("boleta",venta.getIdventa())).willReturn(true);

        //When
        MockHttpServletResponse response = mockMvc.perform(put("/editTipo?idventa=1&tipo=boleta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }
    */
    /*
    //editarTipo caso Bad Request
    @Test
    void siInvocoEditarTipoYHayNulosDebeDevolverStatusBadRequest() throws Exception{
        //Given
        Venta venta = getVenta();
        doThrow(NullPointerException.class).when(ventaService).editarTipo("boleta",1);
        //When
        MockHttpServletResponse response = mockMvc.perform(put("/editTipo?idventa=1&tipo=boleta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());

    }
    */
    /*
    //editarMetodoPago caso OK
    @Test
    void siInvocoEditarMetodoPagoYNoHayNulosDebeDevolverStatusOK() throws Exception{
        //Given
        Venta venta = getVenta();
        given(ventaService.editarMetodoPago("efectivo",venta.getIdventa())).willReturn(true);

        //When
        MockHttpServletResponse response = mockMvc.perform(put("/editMetodoPago?idventa=1&metodopago=efectivo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }
    */
    /*
    //editarMetodoPago caso BAD REQUEST
    @Test
    void siInvocoEditarMetodoPagoYHayNulosDebeDevolverStatusBADREQUEST() throws Exception{
        //Given
        Venta venta = getVenta();
        doThrow(NullPointerException.class).when(ventaService).editarTipo("efectivo",1);

        //When
        MockHttpServletResponse response = mockMvc.perform(put("/editMetodoPago?idventa=1&metodopago=efectivo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }
    */
    Venta getVenta(){
        Venta venta = new Venta();
        venta.setIdventa(1);
        venta.setRutcliente("123");

        return venta;
    }
}
