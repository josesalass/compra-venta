package com.queue.demo.model;
import javax.persistence.*;

@Entity
@Table(name = "telefono_rep")
public class TelefonoRepresentante {
	private int telefono;
	private String rutrep;
	private String rutemp;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
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
