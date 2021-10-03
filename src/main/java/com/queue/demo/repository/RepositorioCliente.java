package com.queue.demo.repository;

import com.queue.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RepositorioCliente extends JpaRepository <Cliente, Integer>{

}
