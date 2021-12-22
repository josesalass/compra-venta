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
    @Column(name="año_mes")
    private String fecha;

    @Column(name="promedio_mensual")
    private int promedio;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPromedio() {
        return promedio;
    }

    public void setPromedio(int promedio) {
        this.promedio = promedio;
    }
}
