package com.queue.demo.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "egresospormes")
public class ViewEgresosPorMes implements Serializable {
    @Id
    @Column(name = "mes")
    private String mes;
    @Column(name = "egresos")
    private int egresos;

    public String getMes() {
        return mes;
    }

    public int getEgresos() {
        return egresos;
    }
}
