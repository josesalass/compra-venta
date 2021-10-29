package com.queue.demo.repository;

import com.queue.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RepositorioProducto extends JpaRepository <Producto, Integer>{

	@Modifying
	@Transactional
	@Query(value = "update producto set stock = (stock+:cantidad) where idproducto = :id" , 
	  nativeQuery = true)
	void editarStock(@Param (value= "id")int id , @Param (value= "cantidad") int cantidad);
	
}
