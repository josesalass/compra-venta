package com.queue.demo.service;

import com.queue.demo.model.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class CompraServiceImpl implements CompraService{

    @Autowired
    RepositorioCompra repCompra;

    @Override
    public List<Compra> buscarTodasLasCompras() {
        return repCompra.findAll();
    }

    @Override
    public Compra buscarCompraPorId(int idCompra) {
        return repCompra.findById(1).get();
    }

    @Override
    public void guardar(Compra compra) {
        repCompra.save(compra);
    }

    @Override
    public void borrarCompraPorId(int idCompra) {
        repCompra.deleteById(1);
    }
}
