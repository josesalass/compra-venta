package com.queue.demo.repository;

import com.queue.demo.model.TelefonoRepresentante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioTelefonoRepresentante extends JpaRepository<TelefonoRepresentante, Integer>{
	int deleteByrutemp(String rut);
	int deleteByrutrep(String rut);
	int deleteBytelefono(int telefono);
}
