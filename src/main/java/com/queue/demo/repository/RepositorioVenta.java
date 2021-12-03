package com.queue.demo.repository;

import com.queue.demo.model.Venta;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RepositorioVenta extends JpaRepository <Venta, Integer>{
	/*
	@Modifying
	@Transactional
	@Query(value = "update venta set fecha = :fecha where idventa = :idventa" , 
	      nativeQuery = true)
	    void editarFecha(@Param (value= "idventa")int idventa , @Param (value= "fecha") Timestamp fecha); 
	
	@Modifying
	@Transactional
	@Query(value = "update venta set tipoventa = :tipoventa where idventa = :idventa" , 
	      nativeQuery = true)
	    void editarTipo(@Param (value= "idventa")int idventa , @Param (value= "tipoventa") String tipoventa); 
	
	@Modifying
	@Transactional
	@Query(value = "update venta set metodopago = :metodopago where idventa = :idventa" , 
	      nativeQuery = true)
	    void editarMetodoPago(@Param (value= "idventa")int idventa , @Param (value= "metodopago") String metodopago); 
	
	*/
}
