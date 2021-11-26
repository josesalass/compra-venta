package com.queue.demo.service;

import java.util.List;
import java.util.Optional;
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
	public Optional<Producto> buscarProductoPorId(int idproducto) {
		Optional<Producto> optional = repProducto.findById(idproducto);
		if (optional.isPresent()) {
			return Optional.of(optional.get());
		}
		return Optional.empty();
	}

	@Override
	public Producto guardarProducto(Producto producto) throws Exception {
		Optional<Producto> OptionalProducto = repProducto.findById(producto.getIdproducto());
		if(OptionalProducto.isPresent()) {
			throw new Exception("Producto con id  "+ producto.getIdproducto()+" ya existe");
		}
		repProducto.save(producto);
        return producto;
    }

	@Override
	public void borrarProductoPorId(int idproducto) {
		repProducto.deleteById(idproducto);
		
	}
	
	@Override
	public Producto actualizarProducto(int idproducto, Producto producto) {
		repProducto.save(producto);
		return producto;
	}


}
