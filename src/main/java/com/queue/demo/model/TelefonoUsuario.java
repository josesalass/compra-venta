package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "telefono_usuario")
public class TelefonoUsuario {
	@Id
	private int telefono;
	private String rutusuario;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name="rutusuario", referencedColumnName="rutusuario",insertable=false, updatable=false)
	})
	private Usuario usuario;

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
