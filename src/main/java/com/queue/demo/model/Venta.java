package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "venta")
public class Venta {
	private int idventa;
	private String fecha;
	private String metodopago;
	private String rutusuario;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdventa() {
		return idventa;
	}
	public void setIdventa(int idventa) {
		this.idventa = idventa;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMetodopago() {
		return metodopago;
	}
	public void setMetodopago(String metodopago) {
		this.metodopago = metodopago;
	}
	public String getRutusuario() {
		return rutusuario;
	}
	public void setRutusuario(String rutusuario) {
		this.rutusuario = rutusuario;
	}
	
}
