package com.queue.demo.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table (name = "proveedor")

public class Proveedor implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private String rutempresa; //Primary Key
	private String nombreempresa;
	private boolean estado;
	
	@OneToOne (mappedBy = "proveedor", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private RepresentanteProveedor representanteProveedor;
	
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
