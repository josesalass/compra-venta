package com.queue.demo.repository;

import com.queue.demo.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioVenta extends JpaRepository <Venta, Integer>{
	
}
