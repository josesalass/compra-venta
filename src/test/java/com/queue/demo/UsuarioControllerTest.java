package com.queue.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.controller.UsuarioController;
import com.queue.demo.model.Usuario;
import com.queue.demo.repository.RepositorioUsuario;
import com.queue.demo.service.UsuarioService;
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
public class UsuarioControllerTest {
    private JacksonTester<Usuario> jsonEmpleado;
    private MockMvc mockMvc;
    @Mock
    private UsuarioService usuarioService;
    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }
    @Test
    void siInvocoAddUsuarioYFunca()throws Exception{
        // Given
        Usuario usuario = getUsuario();
        given(usuarioService.guardar(any(Usuario.class))).willReturn(usuario);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/usuarios/guardarusuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmpleado.write(usuario).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals(jsonEmpleado.write(usuario).getJson(),response.getContentAsString());

    }
    @Test
    void siInvocoCreateUsuarioYNoFunca() throws Exception {
        // Given
        Usuario usuario = getUsuario();
        doThrow(Exception.class).when(usuarioService).guardar(any(Usuario.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/usuarios/guardarusuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmpleado.write(usuario).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }
    Usuario getUsuario(){
        Usuario usuario=new Usuario();
        usuario.setNombre("AAA");
        usuario.setApellido1("BBB");
        usuario.setApellido2("CCC");
        usuario.setCorreousuario("ajsd");
        usuario.setContrasenia("asiuhdi");
        usuario.setRutusuario("12345678-9");
        usuario.setRolusuario(1);
        return usuario;
    }
}
