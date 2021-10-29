package com.queue.demo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "venta")
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idventa;
	
	@Column(name = "fecha")
	private Timestamp fecha;
	
	@Column(name = "metodopago")
	private String metodopago;
	
	@Column(name = "rutusuario")
	private String rutusuario;
	
	@Column(name = "rutcliente")
	private String rutcliente;

	@Column(name = "tipoventa")
	private String tipoventa;
	
	@ManyToOne
    @JoinColumn(name = "rutcliente", insertable=false, updatable=false)
    private Cliente cliente;
	
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	 private Collection<Asociada_Venta> ventaproductos = new ArrayList<>();

	public int getIdventa() {
		return idventa;
	}

	public void setIdventa(int idventa) {
		this.idventa = idventa;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
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
	public Collection<Asociada_Venta> getVentaproductos() {
		return ventaproductos;
	}

	public void setVentaproductos(Collection<Asociada_Venta> ventaproductos) {
		this.ventaproductos = ventaproductos;
	}
	


	
}
