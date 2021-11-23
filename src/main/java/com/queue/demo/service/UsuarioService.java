package com.queue.demo.service;

import java.util.List;


import com.queue.demo.model.Usuario;

public interface UsuarioService {
	public List<Usuario> buscarTodosLosUsuarios();
    public Usuario buscarUsuarioPorRut(String rut);
    public Usuario guardar(Usuario usuario) throws Exception;
    public void borrarUsuarioPorRut(String rut);
}
