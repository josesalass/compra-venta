package com.queue.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
    @Column(name = "rutusuario")
	private String rutusuario;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellido1")
	private String apellido1;
	
	@Column(name = "apellido2")
	private String apellido2;
	
	@Column(name = "correousuario")
	private String correousuario;
	
	@Column(name = "rolusuario")
	private int rolusuario;
	
	@Column(name = "contrasenia")
	private String contrasenia;

	@OneToMany(mappedBy="usuario",cascade=CascadeType.ALL)
	private List<TelefonoUsuario> telefonosusuario=new ArrayList<TelefonoUsuario>();

	public Usuario(String rutusuario, String nombre, String apellido1, String apellido2, String correousuario, int rolusuario, String contrasenia) {
		this.rutusuario = rutusuario;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.correousuario = correousuario;
		this.rolusuario = rolusuario;
		this.contrasenia = contrasenia;
	}



	public Usuario() {
	}

	public String getRutusuario() {
		return rutusuario;
	}
	public void setRutusuario(String rutusuario) {
		this.rutusuario = rutusuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getCorreousuario() {
		return correousuario;
	}
	public void setCorreousuario(String correousuario) {
		this.correousuario = correousuario;
	}
	public int getRolusuario() {
		return rolusuario;
	}
	public void setRolusuario(int rolusuario) {
		this.rolusuario = rolusuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public List<TelefonoUsuario> getTelefonosusuario() {
		return telefonosusuario;
	}
	public void setTelefonosusuario(List<TelefonoUsuario> telefonosusuario) {
		this.telefonosusuario = telefonosusuario;
	}
}
