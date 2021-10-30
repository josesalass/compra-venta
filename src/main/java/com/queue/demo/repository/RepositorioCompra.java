package com.queue.demo.repository;

import com.queue.demo.model.Compra;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface RepositorioCompra extends JpaRepository <Compra, Integer>{
	  	@Modifying
	  	@Transactional
	  	@Query(value = "update compra set fecha = :fecha where idcompra = :idcompra" , 
	  	      nativeQuery = true)
	  	    void editarFecha(@Param (value= "idcompra")int idcompra , @Param (value= "fecha") Timestamp fecha); 
	  	
	  	@Modifying
	  	@Transactional
	  	@Query(value = "update compra set rutempresa = :rutempresa where idcompra = :idcompra" , 
	  	      nativeQuery = true)
	  	    void editarRutEmpresa(@Param (value= "idcompra")int idcompra , @Param (value= "rutempresa") String rutempresa); 
}
