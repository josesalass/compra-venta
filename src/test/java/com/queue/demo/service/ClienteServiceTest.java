package com.queue.demo.service;

import com.queue.demo.model.Cliente;
import com.queue.demo.repository.RepositorioCliente;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    @Mock
    RepositorioCliente repositorioCliente;

    @InjectMocks
    ClienteServiceImpl clienteService;

    //buscarTodosLosClientes caso exitoso
    @Test
    void siInvocoBuscarTodosLosClientesDebeRetornarClienteList(){
        List<Cliente> resultado;
        List<Cliente> clientes = getClientes();
        when(repositorioCliente.findAll()).thenReturn(clientes);

        resultado = clienteService.buscarTodosLosClientes();

        assertNotNull(resultado);
        assertEquals(resultado.size(),clientes.size());
        assertEquals(resultado.get(0).getRutcliente(),clientes.get(0).getRutcliente());
        assertEquals(resultado.get(1).getRutcliente(),clientes.get(1).getRutcliente());
    }

    //buscarTodosLosClientes caso no hay clientes
    @Test
    void siInvocoBuscarTodosLosClientesYNoHayClientesDebeRetornarListaVacia(){
        List<Cliente> resultado;
        List<Cliente> clientes = new ArrayList<>();
        when(repositorioCliente.findAll()).thenReturn(clientes);

        resultado = clienteService.buscarTodosLosClientes();

        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }

    //buscarClientePorRut caso exitoso
    @Test
    void siInvocoBuscarClientePorRutYExisteUnClienteConEseRutDebeRetornarCliente(){
        Optional<Cliente> resultado;
        Optional<Cliente> cliente = Optional.of(getClientes().get(0));
        when(repositorioCliente.findById("1")).thenReturn(cliente);

        resultado = clienteService.buscarClientePorRut("1");

        assertNotNull(resultado);
        assertEquals(cliente.get().getRutcliente(),resultado.get().getRutcliente());
    }

    //buscarClientePorRut caso no existe cliente con ese rut
    @Test
    void siInvocoBuscarClientePorRutYNoExisteUnClienteConEseRutDebeRetornarOptionalVacio(){
        Optional<Cliente> resultado;
        Optional<Cliente> cliente = Optional.empty();
        when(repositorioCliente.findById("1")).thenReturn(cliente);

        resultado = clienteService.buscarClientePorRut("1");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

    }

    //guardar caso exitoso
    @Test
    void siInvocoGuardarDebeGuardarYRetornarCliente(){
        Optional<Cliente> resultado;
        Optional<Cliente> cliente = Optional.of(getClientes().get(0));

        when(repositorioCliente.findById(cliente.get().getRutcliente())).thenReturn(Optional.empty());
        when(repositorioCliente.save(any(Cliente.class))).thenReturn(cliente.get());

        resultado = clienteService.guardar(cliente.get());

        assertNotNull(resultado);
        assertEquals(cliente.get().getRutcliente(),resultado.get().getRutcliente());
        verify(repositorioCliente).save(any(Cliente.class));
    }

    //guardar caso el cliente ya existe
    @Test
    void siInvocoGuardarYElClienteYaExisteDebeRetornarOptionalVacio(){
        Optional<Cliente> resultado;
        Optional<Cliente> cliente = Optional.of(getClientes().get(0));

        when(repositorioCliente.findById(cliente.get().getRutcliente())).thenReturn(cliente);

        resultado = clienteService.guardar(cliente.get());

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    //borrarClientePorRut caso exitoso
    @Test
    void siInvocoBorrarClientePorRutYElClienteExisteDebeBorrarloYRetornarTrue(){
        boolean resultado;
        Optional<Cliente> cliente = Optional.of(getClientes().get(0));

        when(repositorioCliente.findById(cliente.get().getRutcliente())).thenReturn(cliente);


        resultado = clienteService.borrarClientePorRut(cliente.get().getRutcliente());

        assertTrue(resultado);

    }

    //borrarClientePorRut caso no existe el cliente
    @Test
    void siInvocoBorrarClientePorRutYElClienteNoExisteDebeRetornarFalse(){
        boolean resultado;
        Optional<Cliente> cliente = Optional.of(getClientes().get(0));

        when(repositorioCliente.findById(cliente.get().getRutcliente())).thenReturn(Optional.empty());


        resultado = clienteService.borrarClientePorRut(cliente.get().getRutcliente());

        assertFalse(resultado);
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
