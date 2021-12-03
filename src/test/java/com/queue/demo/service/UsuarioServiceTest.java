package com.queue.demo.service;


import com.queue.demo.model.Usuario;
import com.queue.demo.repository.RepositorioUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;


    @Test
    void siInvocoBuscarTodosLosUsuariosDebeRetornarList() {
        // Arrange
        List<Usuario> resultado;
        List<Usuario> usuarios = getListaUsuarios();
        when(repositorioUsuario.findAll()).thenReturn(usuarios);

        // Act
        resultado = usuarioService.buscarTodosLosUsuarios();

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarios.size(),resultado.size());
        assertEquals(usuarios.get(0).getRutusuario(),resultado.get(0).getRutusuario());
        assertEquals(usuarios.get(1).getRutusuario(),resultado.get(1).getRutusuario());
    }


    @Test
    void siInvocoFindUsuarioByRutYExisteElUsuarioDebeRetornarUsuario() {
        // Arrange
        Optional<Usuario> resultado;
        Usuario usuario = getUsuario();
        when(repositorioUsuario.findById(usuario.getRutusuario())).thenReturn(Optional.of(usuario));

        // Act
        resultado = usuarioService.buscarUsuarioPorRut(usuario.getRutusuario());

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
        assertEquals(usuario.getRutusuario(),resultado.get().getRutusuario());
    }


    @Test
    void siInvocoFindUsuarioByRutYNoExisteElUsuarioConEseIdDebeRetornarOptionalVacio() {
        // Arrange
        Optional<Usuario> resultado;
        when(repositorioUsuario.findById("")).thenReturn(Optional.empty());

        // Act
        resultado = usuarioService.buscarUsuarioPorRut("");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void siInvocoGuardarUsuarioDebeGuardarloSatisfactoriamente() throws Exception {
        // Arrange
        Usuario usuario = getUsuario();
        given(usuarioService.guardar(usuario)).willAnswer(invocation -> invocation.getArgument(0));

        // Act
        Usuario usuarioguardado = usuarioService.guardar(usuario);

        // Assert
        assertThat(usuarioguardado).isNotNull();
        verify(repositorioUsuario).save(any(Usuario.class));
    }


    @Test
    void siInvocoGuardarUsuarioDebeRetornarException() throws Exception {
        // Arrange
        Usuario usuario = null;

        // Assert
        assertThrows(Exception.class,() -> {
            usuarioService.guardar(usuario);
        });

        verify(repositorioUsuario, never()).save(any(Usuario.class));
    }


    @Test
    void siInvocoBorrarUsuarioDebeBorrarloSatisfactoriamente() {
        // Arrange
        Usuario usuario = getUsuario();
        String rutusuario = "prueba";


        usuarioService.borrarUsuarioPorRut(rutusuario);
        usuarioService.borrarUsuarioPorRut(rutusuario);


        // Assert
        verify(repositorioUsuario, times(2)).deleteById(rutusuario);
    }



    private Usuario getUsuario() {
        Usuario usuario = new Usuario("123","jose","apellido1","tres","ada@gmail.com",0,"producto1");
        return  usuario;
    }


    private List<Usuario> getListaUsuarios() {
        Usuario usuario1 = new Usuario("123","jose","apellido1","tres","ada@gmail.com",0,"producto1");
        Usuario usuario2 = new Usuario("2321","aa","peres","dias","ada2@gmail.com",1,"producto2");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        return  usuarios;
    }

}


