package com.queue.demo.service;

import com.queue.demo.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import com.queue.demo.repository.*;

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
    public Optional<Cliente> buscarClientePorRut(String rut) {
        Optional<Cliente> cliente = repCliente.findById(rut);
        if (cliente.isPresent()){
            return Optional.of(cliente.get());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Cliente> guardar(Cliente cliente) {
        Optional<Cliente> clienteNuevo = repCliente.findById(cliente.getRutcliente());
        if (clienteNuevo.isEmpty()){
            return Optional.of(repCliente.save(cliente));
        }
        return Optional.empty(); //No realiza el guardado debido a que el cliente existe
    }

    @Override
    public boolean borrarClientePorRut(String rut) {
        Optional<Cliente> cliente = buscarClientePorRut(rut);
        if(!cliente.isEmpty()){
            repCliente.delete(cliente.get());
            return true;
        }
        return false;
    }
}
