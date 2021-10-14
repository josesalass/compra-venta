package com.queue.demo.repository;

import com.queue.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {
	
}
