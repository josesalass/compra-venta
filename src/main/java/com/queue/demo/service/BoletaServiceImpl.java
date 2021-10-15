package com.queue.demo.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.queue.demo.model.Boleta;
import com.queue.demo.repository.RepositorioBoleta;

@Service
@Transactional
public class BoletaServiceImpl implements BoletaService {
	
	@Autowired
	RepositorioBoleta repBoleta;
	
	@Override
	public List<Boleta> buscarTodasLasBoletas() {
		return repBoleta.findAll();
	}

}
