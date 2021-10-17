package com.queue.demo.repository;

import com.queue.demo.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface RepositorioProveedor extends JpaRepository <Proveedor,Integer> {
	
	int deleteBynombreempresa(String nombre);
	int deleteByrutempresa(String rut);
}
