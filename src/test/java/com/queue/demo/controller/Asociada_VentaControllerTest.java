package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Asociada_Venta;
import com.queue.demo.model.IdAsociadaVenta;
import com.queue.demo.service.Asociada_VentaServiceImpl;
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
public class Asociada_VentaControllerTest {
    private MockMvc mockMvc;
    @Mock
    private Asociada_VentaServiceImpl asociadaVentaService;
    @InjectMocks
    private Asociada_VentaController asociadaVentaController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(asociadaVentaController).build();
    }

    @Test
    void siInvocoFuncionListDebeRetornarTodasLasAsociadaVenta() throws Exception{
        List<Asociada_Venta> avs = getAVs();

        given(asociadaVentaService.buscarTodasLasVentasAsociadas()).willReturn(avs);

        MockHttpServletResponse response= mockMvc.perform(get("/asociada_Venta")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoFuncionListDebeRetornarListaVacia() throws Exception{
        List<Asociada_Venta> avs = new ArrayList<>();

        given(asociadaVentaService.buscarTodasLasVentasAsociadas()).willReturn(avs);

        MockHttpServletResponse response= mockMvc.perform(get("/asociada_Venta")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    private List<Asociada_Venta> getAVs(){
        Asociada_Venta av = new Asociada_Venta();
        IdAsociadaVenta id = new IdAsociadaVenta();
        id.setIdproducto(1);
        id.setIdventa(1);
        av.setIdAsociadaVenta(id);
        List<Asociada_Venta> avs = new ArrayList<>();
        avs.add(av);
        return avs;
    }
}
