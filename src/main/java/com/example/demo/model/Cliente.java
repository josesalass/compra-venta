package com.example.demo.model;
import javax.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {
    private String rutcliente;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int telefono;
    private String comuna;
    private String calle;
    private int numerocalle;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getRutcliente() {
        return rutcliente;
    }

    public void setRutcliente(String rutcliente) {
        this.rutcliente = rutcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumerocalle() {
        return numerocalle;
    }

    public void setNumerocalle(int numerocalle) {
        this.numerocalle = numerocalle;
    }
}
