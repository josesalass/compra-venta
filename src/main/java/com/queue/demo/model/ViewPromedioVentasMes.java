package com.queue.demo.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Immutable
@Table(name="promedioventasmes")
public class ViewPromedioVentasMes implements Serializable {
    @Id
    @Column(name="anio_mes")
    private String anio_mes;

    @Column(name="promedio_mensual")
    private int promedio_mensual;

    public String getAnio_mes() {
        return anio_mes;
    }

    public void setAnio_mes(String anio_mes) {
        this.anio_mes = anio_mes;
    }

    public int getPromedio_mensual() {
        return promedio_mensual;
    }

    public void setPromedio_mensual(int promedio_mensual) {
        this.promedio_mensual = promedio_mensual;
    }
}
