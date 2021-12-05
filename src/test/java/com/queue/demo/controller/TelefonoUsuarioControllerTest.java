package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Compra;
import com.queue.demo.model.Proveedor;
import com.queue.demo.model.TelefonoUsuario;
import com.queue.demo.model.Usuario;
import com.queue.demo.service.TelefonoUsuarioServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class TelefonoUsuarioControllerTest {

    private JacksonTester<TelefonoUsuario> jsonTelefonousu;
    private MockMvc mockMvc;
    @Mock
    private TelefonoUsuarioService telefonoUsuarioService;

    @InjectMocks
    private TelefonoUsuarioController telefonoUsuarioController;


    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(telefonoUsuarioController).build();
    }

    @Test
    void siInvocoFuncionListDebeRetornarTodosLosTelefonosUsuario() throws Exception {
        List<TelefonoUsuario> tfs = getTelefonoUsu();
        given(telefonoUsuarioService.buscarTodosLosTelefonosUsuarios()).willReturn(tfs);

        MockHttpServletResponse response = mockMvc.perform(get("/telefonos_usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void siInvocoFuncionListDebeRetornarListaVacia() throws Exception {
        List<TelefonoUsuario> tfs = new ArrayList<>();
        given(telefonoUsuarioService.buscarTodosLosTelefonosUsuarios()).willReturn(tfs);

        MockHttpServletResponse response = mockMvc.perform(get("/telefonos_usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void siInvocoSaveTelefonoYSeGuardaCorrectamenteDebeRetornarStatusCREATED() throws Exception{
        TelefonoUsuario tsf= getTelefonoUsu().get(0);

        given(telefonoUsuarioService.guardar(any(TelefonoUsuario.class))).willReturn(tsf);

        MockHttpServletResponse response = mockMvc.perform(post("/telefonos_usuarios/guardartelefono")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTelefonousu.write(tsf).getJson())
                        .accept((MediaType.APPLICATION_JSON)))
                        .andReturn()
                        .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals(jsonTelefonousu.write(tsf).getJson(),response.getContentAsString());

    }
    @Test
    void siInvocoSaveTelefonoYNoSeGuardaDebeRetornarStatusBADREQUEST() throws Exception{
        TelefonoUsuario tsf= null;

        MockHttpServletResponse response = mockMvc.perform(post("/telefonos_usuarios/guardartelefono")
                        .accept((MediaType.APPLICATION_JSON)))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }


    private List<TelefonoUsuario> getTelefonoUsu(){
        List<TelefonoUsuario> list= new ArrayList<>();
        TelefonoUsuario tfs= new TelefonoUsuario();
        tfs.setTelefono(278564732);
        tfs.setRutusuario("183856049");
        list.add(tfs);
        return list;
    }


}
