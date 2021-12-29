package com.queue.demo.controller;

import com.queue.demo.model.Usuario;
import com.queue.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;


	@GetMapping("")
	public List <Usuario> list(){
		return usuarioService.buscarTodosLosUsuarios();
	}

	@RequestMapping(method=RequestMethod.POST, value="/guardarusuario")
	public ResponseEntity<String> addUsuario(@RequestBody Usuario usuario) {
		try {
			if (usuarioService.buscarUsuarioPorRut( usuario.getRutusuario() ) == null) {
				//El usuario no existe, por lo que se puede crear
				usuarioService.guardar(usuario);
				return new ResponseEntity<>("Usuario creado", HttpStatus.CREATED);

			}else {//El usuario existe así que no se puede crear
				return new ResponseEntity<>("Usuario ya existente",HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/login")
	public ResponseEntity<String> login(@PathVariable String rutusuario, @PathVariable String contrasenia){
		Optional<Usuario> us= usuarioService.buscarUsuarioPorRut(rutusuario);
		if (!us.isEmpty()){
			if(us.get().getContrasenia().equals(contrasenia)){
				us.get().setContadorlogin(0);
				return new ResponseEntity<>("Login Exitoso",HttpStatus.ACCEPTED);
			}else{
				if (us.get().getContadorlogin()<5){
					return new ResponseEntity<>("Contraseña incorrecta, tiene:"+(5-us.get().getContadorlogin())+"intentos",HttpStatus.BAD_REQUEST);
				}else{
					return new ResponseEntity<>("Cuenta bloqueada, contacte con el administrador",HttpStatus.BAD_REQUEST);
				}
			}
		}else{
			return new ResponseEntity<>("El rut de usuario no existe",HttpStatus.BAD_REQUEST);
		}
	}
}
