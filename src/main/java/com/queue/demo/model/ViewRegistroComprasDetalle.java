package com.queue.demo.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Immutable
@Table(name = "registrocomprasdetalle")
public class ViewRegistroComprasDetalle implements Serializable {
    @Id
    @Column(name = "idcompra")
    private int idcompra;

    @Column(name = "idproducto")
    private int idproducto;

    @Column(name = "detalleproducto")
    private String detalleproducto;

    @Column(name="fecha")
    private Timestamp fecha;

    @Column(name = "proveedor")
    private String proveedor;

    @Column(name = "admindecompras")
    private String admindecompras;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "valorcompra")
    private int valorcompra;

    @Column(name = "valortotal")
    private int valortotal;

    public int getIdcompra() {
        return idcompra;
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

    public String getProveedor() {
        return proveedor;
    }

    public String getAdmindecompras() {
        return admindecompras;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getValorcompra() {
        return valorcompra;
    }

    public int getValortotal() {
        return valortotal;
    }
}
