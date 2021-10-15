package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "factura")
public class Factura {
	 private String rutcliente;
	 private int idventa;
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 
	 public String getRutcliente() {
	 	return rutcliente;
	 }
	 public void setRutcliente(String rutcliente) {
		this.rutcliente = rutcliente;
	 }
	 public int getIdventa() {
	 	return idventa;
	 }
	 public void setIdventa(int idventa) {
	 	this.idventa = idventa;
	 } 

}
