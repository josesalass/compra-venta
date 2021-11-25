package com.queue.demo.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Immutable
@Table(name="registrocomprasresumen")
public class ViewRegistroComprasResumen implements Serializable {
    @Id
    @Column(name="idcompra")
    private int idcompra;

    @Column(name="fecha")
    private Timestamp fecha;

    @Column(name="proveedor")
    private String proveedor;

    @Column(name="admindecompras")
    private String adminDeCompras;

    @Column(name="valortotal")
    private BigInteger valorTotal;

    public int getIdcompra() {
        return idcompra;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getAdminDeCompras() {
        return adminDeCompras;
    }

    public BigInteger getValorTotal() {
        return valorTotal;
    }
}
