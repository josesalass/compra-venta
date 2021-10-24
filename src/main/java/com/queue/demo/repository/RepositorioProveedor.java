package com.queue.demo.repository;

import com.queue.demo.model.Proveedor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface RepositorioProveedor extends JpaRepository <Proveedor,Integer> {
	
	int deleteBynombreempresa(String nombre);
	int deleteByrutempresa(String rut);
	
	@Modifying
	@Transactional
	@Query(value = "update proveedor set estado = false where nombreempresa = :nombreempresa" , 
	      nativeQuery = true)
	    void ocultarPorNombre(@Param (value= "nombreempresa") String nombreempresa);
	
	@Modifying
	@Transactional
	@Query(value = "update proveedor set estado = false where rutempresa = :rutempresa" , 
	      nativeQuery = true)
	    void ocultarPorRut(@Param (value= "rutempresa") String rutempresa);
}
