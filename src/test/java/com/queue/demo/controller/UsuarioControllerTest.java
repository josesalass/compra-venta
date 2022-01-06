package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Usuario;
import com.queue.demo.model.Venta;
import com.queue.demo.service.UsuarioService;
import com.queue.demo.util.Encriptador;
import org.checkerframework.checker.nullness.Opt;
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

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    private JacksonTester<Usuario> jsonUsuario;
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
    void siInvocoListDebeMostrarTodasLosUsuarios() throws Exception{
        List<Usuario> u = new ArrayList<>();
        u.add(getUsuario());
        given(usuarioService.buscarTodosLosUsuarios()).willReturn(u);

        MockHttpServletResponse response = mockMvc.perform(get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();



        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    //list con lista vacía
    @Test
    void siInvocoListYNoHayUsuariosDebeRetornarNotFound() throws Exception{
        List<Usuario> u = new ArrayList<>();
        given(usuarioService.buscarTodosLosUsuarios()).willReturn(u);

        MockHttpServletResponse response = mockMvc.perform(get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    @Test
    void siInvocoAddUsuarioSeDebeAlmacenarYDevolverElProveedorConStatusCreated()throws Exception{
        // Given
        Usuario usuario = getUsuario();
        given(usuarioService.guardar(any(Usuario.class))).willReturn(usuario);


        // When
        MockHttpServletResponse response = mockMvc.perform(post("/usuarios/guardarusuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUsuario.write(usuario).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());

    }

    @Test
    void siInvocoAddUsuarioYYaExisteDebeDevolverStatusBadRequest()throws Exception{
        // Given
        Usuario usuario = getUsuario();
        given (usuarioService.buscarUsuarioPorRut(usuario.getRutusuario())).willReturn(Optional.of(usuario));
        //given(usuarioService.guardar(any(Usuario.class))).willReturn(usuario);


        // When
        MockHttpServletResponse response = mockMvc.perform(post("/usuarios/guardarusuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUsuario.write(usuario).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());

    }
    @Test
    void siInvocoCreateUsuarioSeDebeDevolverElStatusBadRequest() throws Exception {
        // Given
        Usuario usuario = getUsuario();
        doThrow(Exception.class).when(usuarioService).guardar(any(Usuario.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/usuarios/guardarusuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUsuario.write(usuario).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoLoginYExisteElUsuarioYLaContraseñaEsCorrectaRetornaStatusAccepted() throws Exception {
        Usuario usuario=getUsuario();
        given(usuarioService.buscarUsuarioPorRut(usuario.getRutusuario())).willReturn(Optional.of(usuario));


        MockHttpServletResponse response = mockMvc.perform(put("/usuarios/login/{rutusuario}/{contrasenia}",usuario.getRutusuario(),"asiuhdi")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.ACCEPTED.value(),response.getStatus());
    }

    @Test
    void siInvocoLoginYExisteElUsuarioYLaContraseñaEsIncorrectaYTieneIntentosDisponiblesRetornaStatusBadRequest() throws Exception {
        Usuario usuario=getUsuario();
        given(usuarioService.buscarUsuarioPorRut(usuario.getRutusuario())).willReturn(Optional.of(usuario));


        MockHttpServletResponse response = mockMvc.perform(put("/usuarios/login/{rutusuario}/{contrasenia}",usuario.getRutusuario(),"dasdas")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoLoginYExisteElUsuarioYLaContraseñaEsIncorrectaYNoTieneIntentosDisponiblesRetornaStatusBadRequest() throws Exception {
        Usuario usuario=getUsuario();
        usuario.setContadorlogin(6);
        given(usuarioService.buscarUsuarioPorRut(usuario.getRutusuario())).willReturn(Optional.of(usuario));


        MockHttpServletResponse response = mockMvc.perform(put("/usuarios/login/{rutusuario}/{contrasenia}",usuario.getRutusuario(),"dasdas")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoLoginYNoExisteElUsuarioRetornaStatusBadRequest() throws Exception {
        Usuario usuario=getUsuario();
        usuario.setContadorlogin(6);
        given(usuarioService.buscarUsuarioPorRut(usuario.getRutusuario())).willReturn(Optional.empty());


        MockHttpServletResponse response = mockMvc.perform(put("/usuarios/login/{rutusuario}/{contrasenia}",usuario.getRutusuario(),"dasdas")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }



    Usuario getUsuario() throws Exception {
        Usuario usuario=new Usuario();
        usuario.setNombre("AAA");
        usuario.setApellido1("BBB");
        usuario.setApellido2("CCC");
        usuario.setCorreousuario("ajsd");


        usuario.setContadorlogin(0);
        usuario.setRutusuario("12345678-9");
        usuario.setContrasenia(contraEncript("asiuhdi",usuario.getRutusuario()));
        usuario.setRolusuario(1);
        return usuario;
    }

    String contraEncript(String contrasenia,String rut) throws Exception {
        Key key = Encriptador.generateKey();
        String contra = rut.concat(contrasenia);
        contrasenia = Encriptador.encrypt(contra,key);

        return contrasenia;
    }
}
