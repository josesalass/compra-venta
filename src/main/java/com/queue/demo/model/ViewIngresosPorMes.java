package com.queue.demo.model;


import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "ingresospormes")
public class ViewIngresosPorMes implements Serializable {
    @Id
    @Column(name = "mes")
    private String mes;

    @Column(name = "ingresos")
    private int ingresos;

    public String getMes() {
        return mes;
    }

    public int getIngresos() {
        return ingresos;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }
}
