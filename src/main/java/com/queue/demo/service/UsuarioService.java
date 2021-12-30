package com.queue.demo.service;

import java.util.List;
import java.util.Optional;


import com.queue.demo.model.Usuario;

public interface UsuarioService {
	public List<Usuario> buscarTodosLosUsuarios();
    public Optional<Usuario> buscarUsuarioPorRut(String idusuario);
    public Usuario guardar(Usuario usuario) throws Exception;


    Usuario ajustarIntentoLogin(Usuario usuario, String tipo);

    public void borrarUsuarioPorRut(String rut);
}
