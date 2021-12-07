package com.queue.demo.service;

import java.util.List;
import java.util.Optional;

import com.queue.demo.model.TelefonoUsuario;

public interface TelefonoUsuarioService {
	public List<TelefonoUsuario> buscarTodosLosTelefonosUsuarios();
	public Optional<TelefonoUsuario> buscarTelefonoUsuarioPorId(int id);
	public TelefonoUsuario guardar(TelefonoUsuario telefonoU) throws Exception;
	public void borrarTelefonoUsuarioPorId(int telefonoU);
	public List<TelefonoUsuario> buscarTelefonoPorRut(String rutUsuario)throws Exception;
}
