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
@Table(name="registroventasresumen")
public class ViewRegistroVentasResumen implements Serializable {
    @Id
    @Column (name="idventa")
    private int idventa;

    @Column (name="fecha")
    private Timestamp fecha;

    @Column (name="cliente")
    private String cliente;

    @Column (name="tipoventa")
    private String tipoventa;

    @Column (name="admindeventas")
    private String admindeventas;

    @Column (name="valortotal")
    private int valortotal;

    public int getIdventa() {
        return idventa;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTipoventa() {
        return tipoventa;
    }

    public String getAdmindeventas() {
        return admindeventas;
    }

    public int getValortotal() {
        return valortotal;
    }
}
