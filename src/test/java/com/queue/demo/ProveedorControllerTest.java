package com.queue.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.controller.ProveedorController;
import com.queue.demo.model.Proveedor;
import com.queue.demo.model.RepresentanteProveedor;
import com.queue.demo.model.Usuario;
import com.queue.demo.service.ProveedorService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class ProveedorControllerTest {
    private JacksonTester<Proveedor> jsonEmpleado;
    private MockMvc mockMvc;
    @Mock
    private ProveedorService proveedorService;
    @InjectMocks
    private ProveedorController proveedorController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(proveedorController).build();
    }
    @Test
    void siInvocoSaveProveedorYFunca() throws Exception {
        // Given
        Proveedor proveedor = getProveedor();
        given(proveedorService.guardarProveedor(any(Proveedor.class))).willReturn(proveedor);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/proveedor/guardarProveedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmpleado.write(proveedor).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals(jsonEmpleado.write(proveedor).getJson(),response.getContentAsString());
    }
    @Test
    void siInvocoCreateUsuarioYNoFunca() throws Exception {
        // Given
        Proveedor proveedor = getProveedor();
        doThrow(Exception.class).when(proveedorService).guardarProveedor(any(Proveedor.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/proveedor/guardarProveedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmpleado.write(proveedor).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }
    Proveedor getProveedor(){
        Proveedor proveedor=new Proveedor();
        RepresentanteProveedor representanteProveedor;
        representanteProveedor=new RepresentanteProveedor();
        representanteProveedor.setEstado(true);
        representanteProveedor.setNombre("askjdb");
        representanteProveedor.setApellido1("iuyabs");
        representanteProveedor.setApellido2("aisudba");
        representanteProveedor.setCorreo("oahsdbahs@gmail.com");
        representanteProveedor.setRutemp("2461389237123-1");
        proveedor.setRepresentanteProveedor(representanteProveedor);
        proveedor.setEstado(true);
        proveedor.setNombreempresa("AAA");
        proveedor.setRutempresa("123456778-9");
        return proveedor;
    }
}
