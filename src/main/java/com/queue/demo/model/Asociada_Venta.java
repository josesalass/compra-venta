package com.queue.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "Asociada_Venta")
public class Asociada_Venta {
    private int idProducto;
    private int idVenta;
    private int cantidad;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getidProducto() {
        return idProducto;
    }

    public void setidProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getidVenta() {
        return idVenta;
    }

    public void setidVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getcantidad() {
        return cantidad;
    }

    public void setcantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
