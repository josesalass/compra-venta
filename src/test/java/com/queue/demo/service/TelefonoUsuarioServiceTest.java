package com.queue.demo.service;


import com.queue.demo.model.*;
import com.queue.demo.repository.RepositorioAsociada_Venta;
import com.queue.demo.repository.RepositorioRepresentanteProveedor;
import com.queue.demo.repository.RepositorioTelefonoUsuario;
import com.queue.demo.repository.RepositorioUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TelefonoUsuarioServiceTest {

    @Mock
    private RepositorioTelefonoUsuario repositorioTelefonoUsuario;
    @Mock
    RepositorioUsuario repositorioUsuario;
    @InjectMocks
    private TelefonoUsuarioServiceImpl telefonoUsuarioService;
    @Mock
    private UsuarioServiceImpl usuarioService;

    @Test
    void siInvocoBuscarTodosLosTelefonosYExistenTelefonosDebeRetornarUnaListaConLosTelefonos() throws Exception {
        List <TelefonoUsuario> resultado;
        List <TelefonoUsuario> telefonos= getTelefonoUsu();
        when(telefonoUsuarioService.buscarTodosLosTelefonosUsuarios()).thenReturn(telefonos);
        // Act
        resultado = telefonoUsuarioService.buscarTodosLosTelefonosUsuarios();
        // Assert
        assertNotNull(resultado);
        assertEquals(telefonos.size(),resultado.size());

    }
    @Test
    void siInvocoBuscarTodosLosTelefonosYExistenTelefonosDebeRetornarUnaListaVacia() throws Exception {
        List <TelefonoUsuario> resultado;
        List <TelefonoUsuario> telefono= new ArrayList<>();
        when(telefonoUsuarioService.buscarTodosLosTelefonosUsuarios()).thenReturn(telefono);
        // Act
        resultado = telefonoUsuarioService.buscarTodosLosTelefonosUsuarios();
        // Assert
        assertNotNull(resultado);
        assertEquals(0,resultado.size());

    }
    @Test
    void siInvocoBuscarTelefonoPorIdYExisteElTelefonoDebeRetornarElTelefono(){
        Optional <TelefonoUsuario> resultado;
        TelefonoUsuario tsf= getTelefonoUsu().get(0);
        when(repositorioTelefonoUsuario.findById(278564732)).thenReturn(Optional.of(tsf));
        resultado= telefonoUsuarioService.buscarTelefonoUsuarioPorId(278564732);

        assertNotNull(resultado);
        assertTrue(resultado.isPresent());

    }
    @Test
    void siInvocoBuscarTelefonoPorIdYNoExisteElTelefonoDebeRetornarOptionalVacio(){
        Optional <TelefonoUsuario> resultado;
        when(repositorioTelefonoUsuario.findById(278564732)).thenReturn(Optional.empty());

        resultado= telefonoUsuarioService.buscarTelefonoUsuarioPorId(278564732);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
    @Test
    void siInvocoBuscarTelefonosPorRutYEncuentraTelefonosDebeRetornarUnaListaConLosTelefonos() throws Exception {
        List <TelefonoUsuario> resultado;
        List <TelefonoUsuario> telefonos= getTelefonoUsu();
        Usuario a= getusuario();
        when(usuarioService.buscarUsuarioPorRut(anyString())).thenReturn(Optional.of(a));
        // Act
        resultado = telefonoUsuarioService.buscarTelefonoPorRut("183856049");
        // Assert
        assertNotNull(resultado);
        assertEquals(telefonos.size(),resultado.size());

    }

    @Test
    void siInvocoBuscarTelefonosPorRutYElRutEsNullDebeRetornarException() throws Exception {

        List <TelefonoUsuario> resultado= new ArrayList<>();

        assertThrows(Exception.class,() -> {
            telefonoUsuarioService.buscarTelefonoPorRut(null);
        });
        assertEquals(0,resultado.size());

    }
    @Test
    void siInvocoBuscarTelefonosPorRutYNoEncuentraElRutUsuarioDebeRetornarException() throws Exception {

        List <TelefonoUsuario> resultado= new ArrayList<>();

        assertThrows(Exception.class,() -> {
            telefonoUsuarioService.buscarTelefonoPorRut("123");
        });
        assertEquals(0,resultado.size());

    }


    @Test
    void siInvocoSaveTelefonoYSeGuardaSatisfactoriamente() throws Exception{

        TelefonoUsuario resultado;
        TelefonoUsuario telefonos= getTelefonoUsu().get(0);
        Usuario a= getusuario();
        when(usuarioService.buscarUsuarioPorRut(anyString())).thenReturn(Optional.of(a));
        given(telefonoUsuarioService.guardar(telefonos)).willReturn(telefonos);

        // Act
        resultado = telefonoUsuarioService.guardar(telefonos);
        // Assert
        assertNotNull(resultado);
        verify(repositorioTelefonoUsuario).save(any(TelefonoUsuario.class));


    }

    @Test
    void SiInvocoSaveTelefonoConDatosNullDebeRetornarException() {

        TelefonoUsuario telefonos= null;

        assertThrows(Exception.class,() -> {
            telefonoUsuarioService.guardar(telefonos);
        });

        verify(repositorioTelefonoUsuario, never()).save(any(TelefonoUsuario.class));

    }

    @Test
    void SiInvocoSaveTelefonoYYaExisteElTelefonoDebeRetornarException() {
        TelefonoUsuario telefonos= new TelefonoUsuario();
        telefonos.setTelefono(278564732);
        assertThrows(Exception.class,() -> {
            telefonoUsuarioService.guardar(telefonos);
        });

        verify(repositorioTelefonoUsuario, never()).save(any(TelefonoUsuario.class));
    }

    @Test
    void SiInvocoSaveTelefonoYElUsuarioNoExisteDebeRetornarException() {
        TelefonoUsuario telefonos= new TelefonoUsuario();
        telefonos.setRutusuario("75830572");
        assertThrows(Exception.class,() -> {
            telefonoUsuarioService.guardar(telefonos);
        });

        verify(repositorioTelefonoUsuario, never()).save(any(TelefonoUsuario.class));
    }

    @Test
    void SiInvocoEliminarPorIdDebeEliminarElNumeroDelUsuario(){
        int id=getTelefonoUsu().get(0).getTelefono();
        telefonoUsuarioService.borrarTelefonoUsuarioPorId(id);
        telefonoUsuarioService.borrarTelefonoUsuarioPorId(id);

        verify(repositorioTelefonoUsuario,times(2)).deleteById(id);
    }

    //datos para test

    private List<TelefonoUsuario> getTelefonoUsu(){
        List<TelefonoUsuario> list= new ArrayList<>();
        TelefonoUsuario tfs= new TelefonoUsuario();
        tfs.setTelefono(278564732);
        tfs.setRutusuario("183856049");
        list.add(tfs);
        return list;
    }
    private  Usuario getusuario(){
        Usuario u= new Usuario();
        u.setApellido1("");
        u.setApellido2("");
        u.setRutusuario("183856049");
        u.setNombre("alfredo");
        u.setContrasenia("123");
        u.setCorreousuario("asdads");
        u.setRolusuario(1);
        List<TelefonoUsuario> a= getTelefonoUsu();
        u.setTelefonosusuario(a);
        return u;
    }

}
