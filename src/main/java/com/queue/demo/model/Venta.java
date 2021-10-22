package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "venta")
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idventa;
	
	@Column(name = "fecha")
	private String fecha;
	
	@Column(name = "metodopago")
	private String metodopago;
	
	@Column(name = "rutusuario")
	private String rutusuario;
	
	@Column(name = "rutcliente")
	private String rutcliente;

	@Column(name = "tipoventa")
	private String tipoventa;
	
	@ManyToOne
    @JoinColumn(name = "rutcliente")
    private Cliente cliente;

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

	public String getRutcliente() {
		return rutcliente;
	}

	public void setRutcliente(String rutcliente) {
		this.rutcliente = rutcliente;
	}

	public String getTipoventa() {
		return tipoventa;
	}

	public void setTipoventa(String tipoventa) {
		this.tipoventa = tipoventa;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	


	
}
