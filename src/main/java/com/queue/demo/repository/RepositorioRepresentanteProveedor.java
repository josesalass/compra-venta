package com.queue.demo.repository;

import com.queue.demo.model.RepresentanteProveedor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface RepositorioRepresentanteProveedor extends JpaRepository <RepresentanteProveedor, Integer>{
	
	int deleteByrutemp(String rut);
	int deleteByrutrep(String rut);
	

}