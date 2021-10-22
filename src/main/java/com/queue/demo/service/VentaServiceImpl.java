package com.queue.demo.service;

import com.queue.demo.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.queue.demo.repository.*;

@Service
@Transactional
public class VentaServiceImpl implements VentaService{
	
	@Autowired
	RepositorioVenta repVenta;
	
	@Override
	public List<Venta> buscarTodasLasVentas(){
		return repVenta.findAll();
	}
	
	@Override
	public Venta buscarVentaPorId(int id) {
		return repVenta.findById(id).get();
	}
	
	@Override
	public void guardar(Venta venta) {
		repVenta.save(venta);
	}
	
	@Override
	public void borrarVentaPorId(int id) {
		repVenta.deleteById(id);
	}

	@Override
	public void addVenta(Venta venta) {
		repVenta.save(venta);
		
	}
}
