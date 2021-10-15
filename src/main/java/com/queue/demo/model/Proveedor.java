package com.queue.demo.model;

import javax.persistence.*;


@Entity
@Table (name = "proveedor")

public class Proveedor {
	private String rutempresa;
	private String nombreempresa;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	public String getRutempresa() {
		return rutempresa;
	}
	public void setRutempresa(String rutempresa) {
		this.rutempresa = rutempresa;
	}
	public String getNombreempresa() {
		return nombreempresa;
	}
	public void setNombreempresa(String nombreempresa) {
		this.nombreempresa = nombreempresa;
	}
	
	
}
