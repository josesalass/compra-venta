package com.queue.demo.service;

import java.util.List;
import com.queue.demo.model.TelefonoUsuario;

public interface TelefonoUsuarioService {
	public List<TelefonoUsuario> buscarTodosLosTelefonosUsuarios();
	public TelefonoUsuario buscarTelefonoUsuarioPorId(int id);
	public void guardar(TelefonoUsuario telefonoU);
	public void borrarTelefonoUsuarioPorId(int telefonoU);
}
