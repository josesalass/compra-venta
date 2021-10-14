package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
	private String rutusuario;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String correousuario;
	private int rolusuario;
	private String contrasenia;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
	

	
	
}
