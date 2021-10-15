package com.queue.demo.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.queue.demo.model.Producto;
import com.queue.demo.repository.RepositorioProducto;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    RepositorioProducto repProducto;
	
	
	@Override
	public List<Producto> buscarTodosLosProductos() {
		return repProducto.findAll();
	}

	@Override
	public Producto buscarProductoPorId(String idproducto) {
		return repProducto.findById(1).get();
	}

	@Override
	public void guardarProducto(Producto producto) {
		repProducto.save(producto);
	}

	@Override
	public void borrarProductoPorId(String idproducto) {
		repProducto.deleteById(1);
		
	}

}
