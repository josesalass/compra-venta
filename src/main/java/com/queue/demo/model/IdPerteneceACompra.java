package com.queue.demo.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class IdPerteneceACompra implements Serializable {
	
	private int idcompra;
	private int idproducto;
	
	
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
	@Override
	public int hashCode() {
		return Objects.hash(idcompra, idproducto);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdPerteneceACompra other = (IdPerteneceACompra) obj;
		return idcompra == other.idcompra && idproducto == other.idproducto;
	}
	
	
	
	
}
