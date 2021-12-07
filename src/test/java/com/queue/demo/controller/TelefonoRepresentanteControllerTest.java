package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.RepresentanteProveedor;
import com.queue.demo.model.TelefonoRepresentante;
import com.queue.demo.model.TelefonoUsuario;
import com.queue.demo.service.RepresentanteProveedorService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class TelefonoRepresentanteControllerTest {

    private JacksonTester<TelefonoRepresentante> jsonTelefonoRep;
    private MockMvc mockMvc;
    @Mock
    private TelefonoRepresentanteServiceImpl telefonoRepresentanteService;

    @InjectMocks
    private TelefonoRepresentanteController telefonoRepresentanteController;

    @Mock
    private RepresentanteProveedorService representanteProveedorService;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(telefonoRepresentanteController).build();
    }

    @Test
    void siInvocoFuncionListDebeRetornarTodosLosTelefonosRepresentanteProveedor() throws Exception{
        List<TelefonoRepresentante> tfs = getTelefonorep();
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

    @Test
    void siInvocoSaveTelefonoYSeGuardaCorrectamenteDebeRetornarStatusCREATED() throws Exception{
        TelefonoRepresentante tsf= getTelefonorep().get(0);
        Optional <RepresentanteProveedor> p= Optional.of(new RepresentanteProveedor());
        p.get().setRutrep(tsf.getRutrep());
        given(representanteProveedorService.buscarRepresentantePorRut(anyString())).willReturn(p);
        given(telefonoRepresentanteService.guardar(any(TelefonoRepresentante.class))).willReturn(tsf);

        MockHttpServletResponse response = mockMvc.perform(post("/telefonorepresentante/guardartelefono")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTelefonoRep.write(tsf).getJson())
                        .accept((MediaType.APPLICATION_JSON)))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals(jsonTelefonoRep.write(tsf).getJson(),response.getContentAsString());

    }
    @Test
    void siInvocoSaveTelefonoYNoSeGuardaDebeRetornarStatusBADREQUEST() throws Exception{
        TelefonoUsuario tsf= null;

        MockHttpServletResponse response = mockMvc.perform(post("/telefonorepresentante/guardartelefono")
                        .accept((MediaType.APPLICATION_JSON)))
                .andReturn()
                .getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }


    private List<TelefonoRepresentante> getTelefonorep(){
        List<TelefonoRepresentante> list= new ArrayList<>();
        TelefonoRepresentante tfs= new TelefonoRepresentante();
        tfs.setTelefono(278564732);
        tfs.setRutrep("123579853");
        tfs.setRutemp("123450693");
        list.add(tfs);
        return list;
    }

    private RepresentanteProveedor getRep(){
        RepresentanteProveedor r=new RepresentanteProveedor();
        r.setTelefonosRepresentante(getTelefonorep());
        r.setNombre("a");
        r.setEstado(true);
        r.setRutrep("123579853");
        r.setRutemp("123450693");
        r.setCorreo("");
        r.setApellido2("");
        r.setApellido2("");
        return r;

    }
}
