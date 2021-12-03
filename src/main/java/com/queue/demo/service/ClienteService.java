package com.queue.demo.service;

import java.util.List;
import java.util.Optional;

import com.queue.demo.model.Cliente;

public interface ClienteService {
    public List<Cliente> buscarTodosLosClientes();
    public Optional<Cliente> buscarClientePorRut(String rut);
    public Optional<Cliente> guardar(Cliente cliente);
    public boolean borrarClientePorRut(String rut);
}
