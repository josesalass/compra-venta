package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Cliente;
import com.queue.demo.service.ClienteServiceImpl;
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
public class ClienteControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void siInvocoFuncionListDebeRetornarTodosLosClientes() throws Exception{
        List<Cliente> clientes = getClientes();
        given(clienteService.buscarTodosLosClientes()).willReturn(clientes);

        MockHttpServletResponse response= mockMvc.perform(get("/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoFuncionListDebeRetornarListaVacia() throws Exception{
        List<Cliente> clientes = new ArrayList<>();
        given(clienteService.buscarTodosLosClientes()).willReturn(clientes);

        MockHttpServletResponse response= mockMvc.perform(get("/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    public List<Cliente> getClientes(){
        Cliente cliente = new Cliente();
        List<Cliente> clientes = new ArrayList<>();
        cliente.setRutcliente("1");
        cliente.setNombre("Pedro");
        clientes.add(cliente);
        cliente.setRutcliente("2");
        cliente.setNombre("Álvaro");
        clientes.add(cliente);
        return clientes;
    }
}
