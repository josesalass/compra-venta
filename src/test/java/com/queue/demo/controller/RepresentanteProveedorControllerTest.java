package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.RepresentanteProveedor;
import com.queue.demo.service.RepresentanteProveedorService;
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
public class RepresentanteProveedorControllerTest {
    private MockMvc mockMvc;
    @Mock
    private RepresentanteProveedorService representanteProveedorService;
    @InjectMocks
    private RepresentanteProveedorController  representanteProveedorController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(representanteProveedorController).build();
    }

    @Test
    void siInvocoFuncionListDebeRetornarTodosLosRepresentantesProveedor() throws Exception{
        List<RepresentanteProveedor> reps = getReps();
        given(representanteProveedorService.buscarTodosLosRepresentanteProveedor()).willReturn(reps);

        MockHttpServletResponse response= mockMvc.perform(get("/representantes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(),response.getStatus());

    }

    @Test
    void siInvocoFuncionListDebeRetornarListaVacia() throws Exception{
        List<RepresentanteProveedor> reps = new ArrayList<>();
        given(representanteProveedorService.buscarTodosLosRepresentanteProveedor()).willReturn(reps);

        MockHttpServletResponse response= mockMvc.perform(get("/representantes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());

    }

    private List<RepresentanteProveedor> getReps(){
        List<RepresentanteProveedor> reps = new ArrayList<>();
        RepresentanteProveedor rep = new RepresentanteProveedor();
        rep.setRutrep("1");
        reps.add(rep);

        return reps;
    }
}
