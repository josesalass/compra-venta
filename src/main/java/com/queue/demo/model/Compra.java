package com.queue.demo.model;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "compra")
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcompra") 
	
	private int idcompra;
    private Timestamp fecha;
    private String rutempresa;
    private String rutusuario;

    @OneToMany(mappedBy = "compra")
    private List<PerteneceACompra> pertenece_compra;
           
    
    public int getidCompra() {
        return idcompra;
    }

    public void setidCompra(int idCompra) {
        this.idcompra = idCompra;
    }

    public Timestamp getfecha() {
        return fecha;
    }

    public void setfecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getrutEmpresa() {
        return rutempresa;
    }

    public void setrutEmpresa(String rutEmpresa) {
        this.rutempresa = rutEmpresa;
    }

    public String rutUsuario() {
        return rutusuario;
    }

    public void rutUsuario(String rutUsuario) {
        this.rutusuario = rutUsuario;
    }

   
}
