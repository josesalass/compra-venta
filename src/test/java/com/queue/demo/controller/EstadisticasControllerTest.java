package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Venta;
import com.queue.demo.model.ViewPromedioVentasMes;
import com.queue.demo.service.EstadisticasService;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class EstadisticasControllerTest {
    private JacksonTester<Venta> jsonVenta;
    private MockMvc mockMvc;

    @Mock
    private EstadisticasService estadisticasService;

    @InjectMocks
    private EstadisticasController estadisticasController;

    @BeforeEach
    void setup(){
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(estadisticasController).build();
    }

    @Test
    void siInvocoGetPromedioVentasDebeDevolverStatusOK() throws Exception{
        List<ViewPromedioVentasMes> lista = getViewPromedioVentasMes();
        given(estadisticasService.verPromedioVentas(2021)).willReturn(lista);

        MockHttpServletResponse respuesta = mockMvc.perform(get("/estadisticas/promedioventas?año=2021")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());
    }

    @Test
    void siInvocoGetPromedioVentasYLaListaEsVaciaDebeDevolverStatusBadRequest() throws Exception{
        List<ViewPromedioVentasMes> lista = new ArrayList<>();
        given(estadisticasService.verPromedioVentas(2021)).willReturn(lista);

        MockHttpServletResponse respuesta = mockMvc.perform(get("/estadisticas/promedioventas?año=2021")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(),respuesta.getStatus());
    }

    private List<ViewPromedioVentasMes> getViewPromedioVentasMes() {
        List<ViewPromedioVentasMes> p = new ArrayList<>();
        ViewPromedioVentasMes v = new ViewPromedioVentasMes();
        v.setAnio_mes("2021-01");
        v.setPromedio_mensual(30000);
        p.add(v);
        v.setAnio_mes("2021-02");
        v.setPromedio_mensual(1000);
        p.add(v);
        return p;
    }
}
