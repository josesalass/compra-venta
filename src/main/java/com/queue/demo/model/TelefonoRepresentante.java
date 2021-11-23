package com.queue.demo.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "telefono_rep")
public class TelefonoRepresentante implements Serializable{
	@Id
	private int telefono;
	private String rutrep;
	private String rutemp;
	
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="rutrep", referencedColumnName="rutrep",insertable=false, updatable=false),
		@JoinColumn(name="rutemp",referencedColumnName="rutemp",insertable=false, updatable=false)	
	})
	private RepresentanteProveedor representanteProveedor;

	// metodos getter y setter 
	
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getRutrep() {
		return rutrep;
	}
	public void setRutrep(String rutrep) {
		this.rutrep = rutrep;
	}
	public String getRutemp() {
		return rutemp;
	}
	public void setRutemp(String rutemp) {
		this.rutemp = rutemp;
	}
	
	
	
}
