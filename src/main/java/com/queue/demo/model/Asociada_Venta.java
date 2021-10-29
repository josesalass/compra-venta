package com.queue.demo.model;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Asociada_Venta")
public class Asociada_Venta  {
	
	@EmbeddedId
	private IdAsociadaVenta idAsociadaVenta = new IdAsociadaVenta();
	
	
	@ManyToOne
	@MapsId("idproducto")
	@JoinColumn(name="idproducto")
    private Producto producto;
	
	
	@ManyToOne
	@MapsId("idventa")
	@JoinColumn(name="idventa")
	@JsonIgnore
    private Venta venta;
	
	
    private int cantidad;
    
    
    
    
	public Asociada_Venta() {
	}


	public Asociada_Venta(Producto producto, Venta venta, int cantidad) {
		super();
		this.producto = producto;
		this.venta = venta;
		this.cantidad = cantidad;
	}


	public IdAsociadaVenta getIdAsociadaVenta() {
		return idAsociadaVenta;
	}


	public void setIdAsociadaVenta(IdAsociadaVenta idAsociadaVenta) {
		this.idAsociadaVenta = idAsociadaVenta;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Venta getVenta() {
		return venta;
	}


	public void setVenta(Venta venta) {
		this.venta = venta;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	

}
