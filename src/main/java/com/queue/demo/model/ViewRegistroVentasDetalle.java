package com.queue.demo.model;


import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Immutable
@Table(name="registroventasdetalle")
public class ViewRegistroVentasDetalle implements Serializable {
    @Id
    @Column(name="idventa")
    private int idventa;

    @Column(name="idproducto")
    private int idproducto;

    @Column(name="detalleproducto")
    private String detalleproducto;

    @Column (name="fecha")
    private Timestamp fecha;

    @Column (name="tipoventa")
    private String tipoventa;

    @Column (name="admindeventas")
    private String admindeventas;

    @Column (name="cantidad")
    private int cantidad;

    @Column (name="valorventa")
    private int valorventa;

    @Column (name="valortotal")
    private int valortotal;

    public int getIdventa() {
        return idventa;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public String getDetalleproducto() {
        return detalleproducto;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public String getTipoventa() {
        return tipoventa;
    }

    public String getAdmindeventas() {
        return admindeventas;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getValorventa() {
        return valorventa;
    }

    public int getValortotal() {
        return valortotal;
    }
}
