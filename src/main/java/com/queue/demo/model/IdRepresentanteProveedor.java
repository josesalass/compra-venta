package com.queue.demo.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class IdRepresentanteProveedor implements Serializable {
	private String rutrep;
	private String rutemp;
	
	
	public IdRepresentanteProveedor(String rutrep, String rutemp) {
		this.rutrep = rutrep;
		this.rutemp = rutemp;
	}
	
	public IdRepresentanteProveedor() {

	}
	
	@Override
	public int hashCode() {
		return Objects.hash(rutemp, rutrep);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdRepresentanteProveedor other = (IdRepresentanteProveedor) obj;
		return Objects.equals(rutemp, other.rutemp) && Objects.equals(rutrep, other.rutrep);
	}
	
	
}