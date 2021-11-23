package com.queue.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "producto")
public class Producto {
	
	@Id
	private int idproducto;
	
	@Column(name = "stock")
	private int stock;
	
	@Column(name = "stockmin")
	private int stockmin;
	
	@Column(name = "valorcompra")
	private int valorcompra;
	
	@Column(name = "valorventa")
	private int valorventa;
	
	@Column(name = "detalleproducto")
	private String detalleproducto;
	
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	@JsonIgnore
	private Collection<Asociada_Venta> ventaproductos = new ArrayList<>();
	
	/* @OneToMany(mappedBy = "producto")
    private List<PerteneceACompra> pertenece_compra; */
	
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	@JsonIgnore
	private Collection<PerteneceACompra> compraproductos = new ArrayList<>();

	public Producto() {

	}

	public Producto(int idproducto, int stock, int stockmin, int valorcompra, int valorventa, String detalleproducto) {
		this.idproducto = idproducto;
		this.stock = stock;
		this.stockmin = stockmin;
		this.valorcompra = valorcompra;
		this.valorventa = valorventa;
		this.detalleproducto = detalleproducto;
	}

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
	public Collection<Asociada_Venta> getVentaproductos() {
		return ventaproductos;
	}
	public void setVentaproductos(Collection<Asociada_Venta> ventaproductos) {
		this.ventaproductos = ventaproductos;
	}
	public Collection<PerteneceACompra> getCompraproductos() {
		return compraproductos;
	}
	public void setCompraproductos(Collection<PerteneceACompra> compraproductos) {
		this.compraproductos = compraproductos;
	}
	
	
	
}
