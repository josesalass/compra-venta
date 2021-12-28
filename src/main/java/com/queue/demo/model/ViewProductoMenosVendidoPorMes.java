package com.queue.demo.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Immutable
@Table(name="productomenosvendidopormes")
public class ViewProductoMenosVendidoPorMes implements Serializable {
    @Id
    @Column(name="fecha")
    private String fecha;

    @Column(name="detalleproducto")
    private String detalleproducto;

    @Column(name="cantidad")
    private int cantidad;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalleproducto() {
        return detalleproducto;
    }

    public void setDetalleproducto(String detalleproducto) {
        this.detalleproducto = detalleproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
