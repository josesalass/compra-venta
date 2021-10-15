package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "telefono_usuario")
public class TelefonoUsuario {
	private int telefono;
	private String rutusuario;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getRutusuario() {
		return rutusuario;
	}
	public void setRutusuario(String rutusuario) {
		this.rutusuario = rutusuario;
	}
	
	
	
}
