package com.example.demo.service;

import com.example.demo.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.example.demo.repository.*;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    RepositorioCliente repCliente;

    @Override
    public List<Cliente> buscarTodosLosClientes() {
        return repCliente.findAll();
    }

    @Override
    public Cliente buscarClientePorRut(String rut) {
        return repCliente.findById(1).get();
    }

    @Override
    public void guardar(Cliente cliente) {
        repCliente.save(cliente);
    }

    @Override
    public void borrarClientePorRut(String rut) {
        repCliente.deleteById(1);
    }
}
