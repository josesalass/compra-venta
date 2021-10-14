package com.queue.demo.repository;

import com.queue.demo.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProveedor extends JpaRepository <Proveedor,Integer> {
	
	
}
