package com.queue.demo.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table (name = "proveedor")

public class Proveedor implements Serializable{
	
	
	@Id
	@Column(name = "rutempresa")
	private String rutempresa; 
	
	@Column(name = "nombreempresa")
	private String nombreempresa;
	// variable que utilizamos para la eliminacion logica 
	
	@Column(name = "estado")
	private boolean estado;
	
	@OneToOne (mappedBy = "proveedor", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private RepresentanteProveedor representanteProveedor;
	
	// Metodos getter y setter
	
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
	public boolean getEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public RepresentanteProveedor getRepresentanteProveedor() {
		return representanteProveedor;
	}
	public void setRepresentanteProveedor(RepresentanteProveedor representanteProveedor) {
		this.representanteProveedor = representanteProveedor;
	}
	
	
}
