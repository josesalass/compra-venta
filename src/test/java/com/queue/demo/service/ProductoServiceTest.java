package com.queue.demo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.queue.demo.model.Producto;
import com.queue.demo.repository.RepositorioProducto;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
	@Mock
	private RepositorioProducto repositorioProducto;
	
	@InjectMocks
	private ProductoServiceImpl productoService;
	
	/*
	@Test
	void SiEditoStockDebeActualizar() {
		int response;
		Producto producto = new Producto();
		producto.setIdproducto(15);
		producto.setStockmin(2);
		producto.setStock(1);
	
		
		response=productoService.editarStock(producto.getIdproducto(), 5);
		

		System.out.print(producto.getStock());
		
		assertNotNull(response);
		assertEquals(response, producto.getIdproducto());
		
	}*/
	
	
	

}
