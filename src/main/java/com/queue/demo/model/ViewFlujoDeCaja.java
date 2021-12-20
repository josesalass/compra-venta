package com.queue.demo.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "flujodecaja")
public class ViewFlujoDeCaja implements Serializable {
    @Id
    @Column(name = "mes")
    private String mes;

    @Column(name = "ingresos")
    private int ingresos;

    @Column(name = "egresos")
    private int egresos;

    @Column(name="flujo")
    private int flujo;

    public String getMes() {
        return mes;
    }

    public int getIngresos() {
        return ingresos;
    }

    public int getEgresos() {
        return egresos;
    }

    public int getFlujo() {
        return flujo;
    }
}
