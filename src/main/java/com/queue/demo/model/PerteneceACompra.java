package com.queue.demo.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pertenece_compra")
public class PerteneceACompra {
	
	@EmbeddedId
	private IdPerteneceACompra idPerteneceACompra = new IdPerteneceACompra();
	
	@ManyToOne
	@MapsId("idproducto")
	@JoinColumn(name="idproducto")
    private Producto producto;
	
	@ManyToOne
	@MapsId("idcompra")
	@JoinColumn(name="idcompra")
	@JsonIgnore
    private Compra compra;
	
	
	@Column(name = "cantidad")
	private int cantidad;


	
	public PerteneceACompra() {
	}


	public PerteneceACompra(IdPerteneceACompra idPerteneceACompra, Producto producto, Compra compra, int cantidad) {
		super();
		this.idPerteneceACompra = idPerteneceACompra;
		this.producto = producto;
		this.compra = compra;
		this.cantidad = cantidad;
	}

	public IdPerteneceACompra getIdPerteneceACompra() {
		return idPerteneceACompra;
	}


	public void setIdPerteneceACompra(IdPerteneceACompra idPerteneceACompra) {
		this.idPerteneceACompra = idPerteneceACompra;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Compra getCompra() {
		return compra;
	}


	public void setCompra(Compra compra) {
		this.compra = compra;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
