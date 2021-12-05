package com.queue.demo.service;

import com.queue.demo.model.RepresentanteProveedor;
import com.queue.demo.model.TelefonoRepresentante;
import com.queue.demo.model.TelefonoUsuario;
import com.queue.demo.model.Usuario;
import com.queue.demo.repository.RepositorioRepresentanteProveedor;
import com.queue.demo.repository.RepositorioTelefonoRepresentante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TelefonoRepresentanteServiceTest {

    @Mock
    private RepositorioTelefonoRepresentante repositorioTelefonoRepresentante;

    @Mock
    private RepositorioRepresentanteProveedor repositoriorep;

    @InjectMocks
    private TelefonoRepresentanteServiceImpl telefonorepService;

    @Mock
    private RepresentanteProveedorServiceImpl repService;

    @Test
    void siInvocoBuscarTodosLosTelefonosYExistenTelefonosDebeRetornarUnaListaConLosTelefonos() throws Exception {
        List <TelefonoRepresentante> resultado;
        List <TelefonoRepresentante> telefonos= getTelefonorep();
        when(telefonorepService.buscarTodosLosTelefonos()).thenReturn(telefonos);
        // Act
        resultado = telefonorepService.buscarTodosLosTelefonos();
        // Assert
        assertNotNull(resultado);
        assertEquals(telefonos.size(),resultado.size());

    }
    @Test
    void siInvocoBuscarTodosLosTelefonosYExistenTelefonosDebeRetornarUnaListaVacia() throws Exception {
        List <TelefonoRepresentante> resultado;
        List <TelefonoRepresentante> telefono= new ArrayList<>();
        when(telefonorepService.buscarTodosLosTelefonos()).thenReturn(telefono);
        // Act
        resultado = telefonorepService.buscarTodosLosTelefonos();
        // Assert
        assertNotNull(resultado);
        assertEquals(0,resultado.size());

    }


    @Test
    void siInvocoBuscarTelefonosPorRutYEncuentraTelefonosDebeRetornarUnaListaConLosTelefonos() throws Exception {
        List<TelefonoRepresentante> resultado;
        List <TelefonoRepresentante> telefonos= getTelefonorep();
        List <RepresentanteProveedor> a= new ArrayList<>();
        RepresentanteProveedor r=getRep();
        when(repService.buscarRepresentantePorRut(anyString())).thenReturn(Optional.of(r));

        // Act
        resultado = telefonorepService.buscarTelefonoPorRut("183856049");
        // Assert
        assertNotNull(resultado);
        assertEquals(telefonos.size(),resultado.size());

    }

    @Test
    void siInvocoBuscarTelefonosPorRutYElRutEsNullDebeRetornarException() throws Exception {

        List <TelefonoRepresentante> resultado= new ArrayList<>();

        assertThrows(Exception.class,() -> {
            telefonorepService.buscarTelefonoPorRut(null);
        });
        assertEquals(0,resultado.size());

    }

    @Test
    void siInvocoBuscarTelefonosPorRutYNoEncuentraElRutRepDebeRetornarException() throws Exception {

        List <TelefonoUsuario> resultado= new ArrayList<>();

        assertThrows(Exception.class,() -> {
            telefonorepService.buscarTelefonoPorRut("123");
        });
        assertEquals(0,resultado.size());

    }


    @Test
    void siInvocoSaveTelefonoYSeGuardaSatisfactoriamente() throws Exception{

        TelefonoRepresentante resultado;
        TelefonoRepresentante telefonos= getTelefonorep().get(0);
        RepresentanteProveedor r=getRep();
        when(repService.buscarRepresentantePorRut(anyString())).thenReturn(Optional.of(r));
        given(telefonorepService.guardar(telefonos)).willReturn(telefonos);
        // Act
        resultado = telefonorepService.guardar(telefonos);
        // Assert
        assertNotNull(resultado);
        verify(repositorioTelefonoRepresentante).save(any(TelefonoRepresentante.class));


    }

    @Test
    void SiInvocoSaveTelefonoConDatosNullDebeRetornarException() {

        TelefonoRepresentante telefonos= null;

        assertThrows(Exception.class,() -> {
            telefonorepService.guardar(telefonos);
        });

        verify(repositorioTelefonoRepresentante, never()).save(any(TelefonoRepresentante.class));

    }

    @Test
    void SiInvocoSaveTelefonoYYaExisteElTelefonoDebeRetornarException() {
        TelefonoRepresentante telefonos= new TelefonoRepresentante();
        telefonos.setTelefono(278564732);
        assertThrows(Exception.class,() -> {
            telefonorepService.guardar(telefonos);
        });

        verify(repositorioTelefonoRepresentante, never()).save(any(TelefonoRepresentante.class));
    }

    @Test
    void SiInvocoSaveTelefonoYElUsuarioNoExisteDebeRetornarException() {
        TelefonoRepresentante telefonos= new TelefonoRepresentante();
        telefonos.setRutrep("75830572");
        assertThrows(Exception.class,() -> {
            telefonorepService.guardar(telefonos);
        });

        verify(repositorioTelefonoRepresentante, never()).save(any(TelefonoRepresentante.class));
    }
    @Test
    void SiInvocoEliminarPorRutDebeEliminarElNumeroDelUsuario(){
        String rut =getTelefonorep().get(0).getRutrep();
        telefonorepService.borrarTelefonoPorRut(rut);
        telefonorepService.borrarTelefonoPorRut(rut);

        verify(repositorioTelefonoRepresentante,times(2)).deleteByrutrep(rut);
    }

    //datos para test

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
