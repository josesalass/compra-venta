package com.example.demo.repository;
import com.example.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RepositorioCliente extends JpaRepository <Cliente, Integer>{

}
