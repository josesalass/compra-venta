package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.TelefonoUsuario;
import com.queue.demo.service.TelefonoUsuarioService;
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
public class TelefonoUsuarioControllerTest {
    private MockMvc mockMvc;
    @Mock
    private TelefonoUsuarioService telefonoUsuarioService;

    @InjectMocks
    private TelefonoUsuarioController telefonoUsuarioController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(telefonoUsuarioController).build();
    }

    @Test
    void siInvocoFuncionListDebeRetornarTodosLosTelefonosUsuario() throws Exception{
        List<TelefonoUsuario> tfs = getTelefonos();
        given(telefonoUsuarioService.buscarTodosLosTelefonosUsuarios()).willReturn(tfs);

        MockHttpServletResponse response= mockMvc.perform(get("/telefonos_usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoFuncionListDebeRetornarListaVacia() throws Exception{
        List<TelefonoUsuario> tfs = new ArrayList<>();
        given(telefonoUsuarioService.buscarTodosLosTelefonosUsuarios()).willReturn(tfs);

        MockHttpServletResponse response= mockMvc.perform(get("/telefonos_usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    private List<TelefonoUsuario> getTelefonos(){
        List<TelefonoUsuario> tfs = new ArrayList<>();
        TelefonoUsuario t = new TelefonoUsuario();
        t.setTelefono(1);
        tfs.add(t);
        return tfs;
    }
}
