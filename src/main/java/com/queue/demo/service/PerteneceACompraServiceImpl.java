package com.queue.demo.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.queue.demo.model.PerteneceACompra;
import com.queue.demo.repository.RepositorioPerteneceACompra;

@Service
@Transactional
public class PerteneceACompraServiceImpl implements PerteneceACompraService {

    @Autowired
    RepositorioPerteneceACompra perteneceACompra;

	@Override
	public List<PerteneceACompra> ListarTodasLasCompras() {
		return perteneceACompra.findAll();
	}
	
	

	
}
