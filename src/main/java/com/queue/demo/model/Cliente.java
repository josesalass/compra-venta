package com.queue.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "cliente")
public class Cliente {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rutcliente;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellido1")
    private String apellido1;
    
    @Column(name = "apellido2")
    private String apellido2;
    
    @Column(name = "telefono")
    private int telefono;
    
    @Column(name = "comuna")
    private String comuna;
    
    @Column(name = "calle")
    private String calle;
    
    @Column(name = "numerocalle")
    private int numerocalle;
    
    
    @OneToMany(mappedBy = "cliente") //, cascade = {CascadeType.PERSIST,CascadeType.MERGE}
    private List<Venta> ventas;
    
    
    public Cliente() {}

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
    
    public List<Venta> getVentas() {
        if (ventas == null) {
        	ventas = new ArrayList<>();
        }
        return ventas;
    }

    public void setEmpleados(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public void addVenta(Venta venta) {
        getVentas().add(venta);
        venta.setCliente(this);
    }
}
