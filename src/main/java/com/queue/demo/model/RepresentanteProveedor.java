package com.queue.demo.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rep_proveedor")
public class RepresentanteProveedor implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rutrep;	//Primary Key
    private String rutemp; //Primary key
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private boolean estado;
    
    @OneToOne (fetch=FetchType.LAZY)
    @JoinColumn (name= "rutemp", referencedColumnName="rutempresa",insertable=false, updatable=false)
	private Proveedor proveedor;
    
    
    @OneToMany(mappedBy="representanteProveedor",cascade=CascadeType.ALL)
    private List<TelefonoRepresentante> telefonoRepresentante=new ArrayList<TelefonoRepresentante>();
    
    
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombreEmpresa) {
		this.nombre = nombreEmpresa;
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
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correoRepresentante) {
		this.correo = correoRepresentante;
	}
	public boolean getEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
}

   
