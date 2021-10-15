package com.queue.demo.repository;

import com.queue.demo.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RepositorioFactura extends JpaRepository <Factura, Integer> {

}
