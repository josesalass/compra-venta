package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "compra")
public class Compra {
    private int idCompra;
    private String fecha;
    private String rutEmpresa;
    private String rutUsuario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getidCompra() {
        return idCompra;
    }

    public void setidCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getfecha() {
        return fecha;
    }

    public void setfecha(String fecha) {
        this.fecha = fecha;
    }

    public String getrutEmpresa() {
        return rutEmpresa;
    }

    public void setrutEmpresa(String rutEmpresa) {
        this.rutEmpresa = rutEmpresa;
    }

    public String rutUsuario() {
        return rutUsuario;
    }

    public void rutUsuario(String rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

   
}
