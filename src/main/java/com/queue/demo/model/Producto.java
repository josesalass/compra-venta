package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {
	private int idproducto;
	private int stock;
	private int stockmin;
	private int valorcompra;
	private int valorventa;
	private String detalleproducto;
	
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getStockmin() {
		return stockmin;
	}
	public void setStockmin(int stockmin) {
		this.stockmin = stockmin;
	}
	public int getValorcompra() {
		return valorcompra;
	}
	public void setValorcompra(int valorcompra) {
		this.valorcompra = valorcompra;
	}
	public int getValorventa() {
		return valorventa;
	}
	public void setValorventa(int valorventa) {
		this.valorventa = valorventa;
	}
	public String getDetalleproducto() {
		return detalleproducto;
	}
	public void setDetalleproducto(String detalleproducto) {
		this.detalleproducto = detalleproducto;
	}
	

	
}
