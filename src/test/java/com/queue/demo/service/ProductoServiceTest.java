package com.queue.demo.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import com.queue.demo.model.Producto;
import com.queue.demo.repository.RepositorioProducto;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
	@Mock
	private RepositorioProducto repositorioProducto;
	
	@InjectMocks
	private ProductoServiceImpl productoService;

	@Test
	void siInvocoBuscarTodosLosProductosDebeRetornarList() {
		// Arrange
		List<Producto> resultado;
		List<Producto> productos = getListaProductos();
		when(repositorioProducto.findAll()).thenReturn(productos);

		// Act
		resultado = productoService.buscarTodosLosProductos();

		// Assert
		assertNotNull(resultado);
		assertEquals(productos.size(),resultado.size());
		assertEquals(productos.get(0).getIdproducto(),resultado.get(0).getIdproducto());
		assertEquals(productos.get(1).getIdproducto(),resultado.get(1).getIdproducto());
	}

	@Test
	void siInvocoFindProductoByIdYExisteElProductoDebeRetornarProducto() {
		// Arrange
		Optional<Producto> resultado;
		Producto producto = getProducto();
		when(repositorioProducto.findById(1)).thenReturn(Optional.of(producto));

		// Act
		resultado = productoService.buscarProductoPorId(1);

		// Assert
		assertNotNull(resultado);
		assertTrue(resultado.isPresent());
		assertEquals(producto.getIdproducto(),resultado.get().getIdproducto());
	}

	@Test
	void siInvocoFindProductoByIdYNoExisteElProductoConEseIdDebeRetornarOptionalVacio() {
		// Arrange
		Optional<Producto> resultado;
		when(repositorioProducto.findById(1)).thenReturn(Optional.empty());

		// Act
		resultado = productoService.buscarProductoPorId(1);

		// Assert
		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
	}

	@Test
	void siInvocoGuardarProductoDebeGuardarloSatisfactoriamente() throws Exception {
		// Arrange
		Producto producto = getProducto();
		given(productoService.buscarProductoPorId(producto.getIdproducto())).willReturn(Optional.empty());
		given(productoService.guardarProducto(producto)).willAnswer(invocation -> invocation.getArgument(0));

		// Act
		Producto productoGuardado = productoService.guardarProducto(producto);

		// Assert
		assertThat(productoGuardado).isNotNull();
		verify(repositorioProducto).save(any(Producto.class));

	}

	@Test
	void SiCreoProductoConIdExistenteDebeRetornarException() {
		// Arrange + Act
		Producto producto = getProducto();
		given(productoService.buscarProductoPorId(producto.getIdproducto())).willReturn(Optional.of(producto));

		// Assert
		assertThrows(Exception.class,() -> {
			productoService.guardarProducto(producto);
		});

		verify(repositorioProducto, never()).save(any(Producto.class));
	}

	@Test
	void SiInvodoBorrarProductoPorIdDebeEliminarElProducto() {
		// Arrange + Act
		int idproducto=1;
		productoService.borrarProductoPorId(idproducto);
		productoService.borrarProductoPorId(idproducto);

		// Assert
		verify(repositorioProducto, times(2)).deleteById(idproducto);
	}

	@Test
	void SiInvocoActualizarProductoDebeRetornarElProducto() {
		// Arrange
		Producto producto = getProducto();
		given(repositorioProducto.save(producto)).willReturn(producto);

		// Act
		Producto  productoFinal = productoService.actualizarProducto(producto.getIdproducto(),producto);

		// Assert
		assertThat(productoFinal).isNotNull();
		verify(repositorioProducto).save(any(Producto.class));
	}




	private Producto getProducto() {
		Producto producto = new Producto(1,4,2,3,5,"producto1");
		return  producto;
	}


	private List<Producto> getListaProductos() {
		Producto producto = new Producto(1,4,2,3,5,"producto1");
		Producto producto2 = new Producto(2,4,2,3,5,"producto2");
		List<Producto> productos = new ArrayList<>();
		productos.add(producto);
		productos.add(producto2);
		return  productos;
	}

}
