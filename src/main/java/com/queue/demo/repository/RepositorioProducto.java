package com.queue.demo.repository;

import com.queue.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProducto extends JpaRepository <Producto, Integer>{

}
