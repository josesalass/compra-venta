package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.TelefonoRepresentante;
import com.queue.demo.service.TelefonoRepresentanteServiceImpl;
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
public class TelefonoRepresentanteControllerTest {
    private MockMvc mockMvc;
    @Mock
    private TelefonoRepresentanteServiceImpl telefonoRepresentanteService;

    @InjectMocks
    private TelefonoRepresentanteController telefonoRepresentanteController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(telefonoRepresentanteController).build();
    }

    @Test
    void siInvocoFuncionListDebeRetornarTodosLosTelefonosRepresentanteProveedor() throws Exception{
        List<TelefonoRepresentante> tfs = getTelefonos();
        given(telefonoRepresentanteService.buscarTodosLosTelefonos()).willReturn(tfs);

        MockHttpServletResponse response= mockMvc.perform(get("/telefonorepresentante")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoFuncionListDebeRetornarListaVacia() throws Exception{
        List<TelefonoRepresentante> tfs = new ArrayList<>();
        given(telefonoRepresentanteService.buscarTodosLosTelefonos()).willReturn(tfs);

        MockHttpServletResponse response= mockMvc.perform(get("/telefonorepresentante")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    private List<TelefonoRepresentante> getTelefonos(){
        List<TelefonoRepresentante> tfs = new ArrayList<>();
        TelefonoRepresentante t = new TelefonoRepresentante();
        t.setTelefono(1);
        tfs.add(t);
        return tfs;
    }
}
