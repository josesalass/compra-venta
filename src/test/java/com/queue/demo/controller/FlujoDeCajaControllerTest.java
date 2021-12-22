package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Proveedor;
import com.queue.demo.model.ViewEgresosPorMes;
import com.queue.demo.model.ViewFlujoDeCaja;
import com.queue.demo.model.ViewIngresosPorMes;
import com.queue.demo.service.FlujoDeCajaServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class FlujoDeCajaControllerTest {
    private JacksonTester<Proveedor> jsonProveedor;
    private MockMvc mockMvc;

    @Mock
    private FlujoDeCajaServiceImpl flujoDeCajaService;
    @InjectMocks
    private FlujoDeCajaController flujoDeCajaController;

    @BeforeEach
    void setup(){
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc= MockMvcBuilders.standaloneSetup(flujoDeCajaController).build();
    }

    @Test
    void SiInvocoListFlujoYExisteDebeRetornarList() throws Exception {
        List<ViewFlujoDeCaja> viewFlujoDeCajas = getFlujoDeCaja();
        given(flujoDeCajaService.verFlujoDeCaja()).willReturn(viewFlujoDeCajas);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/flujodecaja")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(respuesta);
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());

    }

    @Test
    void SiInvocoListFlujoYNoExisteDebeRetornarListaVacia() throws Exception {
        List<ViewFlujoDeCaja> viewFlujoDeCajas = new ArrayList<>();
        given(flujoDeCajaService.verFlujoDeCaja()).willReturn(viewFlujoDeCajas);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/flujodecaja")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(),respuesta.getStatus());

    }

    @Test
    void SiInvocoListIngresoYExisteDebeRetornarList() throws Exception {
        List<ViewIngresosPorMes> viewIngresosPorMes = getIngresos();
        given(flujoDeCajaService.verIngresosAnuales()).willReturn(viewIngresosPorMes);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/flujodecaja/ingresos")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(respuesta);
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoListIngresoYNoExisteDebeRetornarListaVacia() throws Exception {
        List<ViewIngresosPorMes> viewIngresosPorMes = new ArrayList<>();
        given(flujoDeCajaService.verIngresosAnuales()).willReturn(viewIngresosPorMes);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/flujodecaja/ingresos")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoListEngresoYExisteDebeRetornarList() throws Exception {
        List<ViewEgresosPorMes> viewEgresosPorMes = getEgresos();
        given(flujoDeCajaService.verEgresosAnuales()).willReturn(viewEgresosPorMes);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/flujodecaja/egresos")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(respuesta);
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoListEgresoYNoExisteDebeRetornarListaVacia() throws Exception {
        List<ViewEgresosPorMes> viewEgresosPorMes = new ArrayList<>();
        given(flujoDeCajaService.verEgresosAnuales()).willReturn(viewEgresosPorMes);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/flujodecaja/egresos")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(),respuesta.getStatus());
    }


    List<ViewIngresosPorMes> getIngresos(){
        List<ViewIngresosPorMes> ingresosPorMes= new ArrayList<>();

        ViewIngresosPorMes viewIngresosPorMes = new ViewIngresosPorMes();
        viewIngresosPorMes.setIngresos(15000);
        viewIngresosPorMes.setMes("Enero");
        ingresosPorMes.add(viewIngresosPorMes);

        viewIngresosPorMes.setIngresos(10000);
        viewIngresosPorMes.setMes("Febrero");
        ingresosPorMes.add(viewIngresosPorMes);

        viewIngresosPorMes.setIngresos(23999);
        viewIngresosPorMes.setMes("Mayo");
        ingresosPorMes.add(viewIngresosPorMes);

        viewIngresosPorMes.setIngresos(34000);
        viewIngresosPorMes.setMes("Diciembre");
        ingresosPorMes.add(viewIngresosPorMes);

        return ingresosPorMes;
    }

    List<ViewEgresosPorMes> getEgresos(){
        List<ViewEgresosPorMes> egresosPorMes= new ArrayList<>();

        ViewEgresosPorMes viewEgresosPorMes = new ViewEgresosPorMes();

        viewEgresosPorMes.setEgresos(5000);
        viewEgresosPorMes.setMes("Enero");
        egresosPorMes.add(viewEgresosPorMes);

        viewEgresosPorMes.setEgresos(2000);
        viewEgresosPorMes.setMes("Marzo");
        egresosPorMes.add(viewEgresosPorMes);

        viewEgresosPorMes.setEgresos(2999);
        viewEgresosPorMes.setMes("Julio");
        egresosPorMes.add(viewEgresosPorMes);

        viewEgresosPorMes.setEgresos(10000);
        viewEgresosPorMes.setMes("Diciembre");
        egresosPorMes.add(viewEgresosPorMes);

        return egresosPorMes;
    }

    List<ViewFlujoDeCaja> getFlujoDeCaja(){
        List<ViewFlujoDeCaja> flujos = new ArrayList<>();
        List<ViewEgresosPorMes> egresos= getEgresos();
        List<ViewIngresosPorMes> ingresos = getIngresos();


        ViewFlujoDeCaja flujo = new ViewFlujoDeCaja();

        flujo.setMes("Enero");
        flujo.setIngresos(ingresos.get(0).getIngresos());
        flujo.setEgresos(egresos.get(0).getEgresos());
        flujos.add(flujo);

        flujo.setMes("Febrero");
        flujo.setIngresos(ingresos.get(1).getIngresos());
        flujos.add(flujo);

        flujo.setMes("Marzo");
        flujo.setEgresos(egresos.get(1).getEgresos());
        flujos.add(flujo);

        flujo.setMes("Mayo");
        flujo.setIngresos(ingresos.get(2).getIngresos());
        flujos.add(flujo);

        flujo.setMes("Julio");
        flujo.setEgresos(egresos.get(2).getEgresos());
        flujos.add(flujo);

        flujo.setMes("Diciembre");
        flujo.setIngresos(ingresos.get(3).getIngresos());
        flujo.setEgresos(egresos.get(3).getEgresos());
        flujos.add(flujo);

        return  flujos;
    }

}
