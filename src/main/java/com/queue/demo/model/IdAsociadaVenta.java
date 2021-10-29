package com.queue.demo.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;


@Embeddable
public class IdAsociadaVenta implements Serializable {
	
	
	private int idproducto;
	private int idventa;
	

	
	public int getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	public int getIdventa() {
		return idventa;
	}
	public void setIdventa(int idventa) {
		this.idventa = idventa;
	}
    
	@Override
	public int hashCode() {
		return Objects.hash(idproducto, idventa);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdAsociadaVenta other = (IdAsociadaVenta) obj;
		return idproducto == other.idproducto && idventa == other.idventa;
	}
	

    
}
