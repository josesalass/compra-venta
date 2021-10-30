package com.queue.demo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "compra")
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcompra") 
	private int idcompra;
	
	@Column(name = "fecha") 
    private Timestamp fecha;
	
	@Column(name = "rutempresa") 
    private String rutempresa;
	
	@Column(name = "rutusuario") 
    private String rutusuario;

  /*  @OneToMany(mappedBy = "compra")
    private List<PerteneceACompra> pertenece_compra; */
    
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
	 private Collection<PerteneceACompra> compraproductos = new ArrayList<>();

	public int getIdcompra() {
		return idcompra;
	}

	public void setIdcompra(int idcompra) {
		this.idcompra = idcompra;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getRutempresa() {
		return rutempresa;
	}

	public void setRutempresa(String rutempresa) {
		this.rutempresa = rutempresa;
	}

	public String getRutusuario() {
		return rutusuario;
	}

	public void setRutusuario(String rutusuario) {
		this.rutusuario = rutusuario;
	}

	public Collection<PerteneceACompra> getCompraproductos() {
		return compraproductos;
	}

	public void setCompraproductos(Collection<PerteneceACompra> compraproductos) {
		this.compraproductos = compraproductos;
	}
           
    
   

   
}
