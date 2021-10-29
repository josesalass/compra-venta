package com.queue.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pertenece_compra")
public class PerteneceACompra {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int idcompra;
	private int idproducto;
	private int cantidad;
		
	
	@ManyToOne
    @JoinColumn(name = "idcompra",insertable=false,updatable=false)
    private Compra compra;
	
	@ManyToOne
    @JoinColumn(name = "idproducto",insertable=false,updatable=false)
    private Producto producto;
	
	public int getIdcompra() {
		return idcompra;
	}
	public void setIdcompra(int idcompra) {
		this.idcompra = idcompra;
	}
	public int getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	

	
	
	
}
