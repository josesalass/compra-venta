package com.example.demo.service;
import java.util.List;
import com.example.demo.model.Cliente;

public interface ClienteService {
    public List<Cliente> buscarTodosLosClientes();
    public Cliente buscarClientePorRut(String rut);
    public void guardar(Cliente cliente);
    public void borrarClientePorRut(String rut);
}
