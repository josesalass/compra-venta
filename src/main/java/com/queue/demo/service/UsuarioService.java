package com.queue.demo.service;

import java.util.List;


import com.queue.demo.model.Usuario;

public interface UsuarioService {
	public List<Usuario> buscarTodosLosUsuarios();
    public Usuario buscarUsuarioPorRut(String rut);
    public void guardar(Usuario usuario);
    public void borrarUsuarioPorRut(String rut);
}
