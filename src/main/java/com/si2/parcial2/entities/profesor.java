package com.si2.parcial2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
public class profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProfesor;
    

    private String nombre;

    private String direccion;

    private Integer telefono;

     @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public User getUser() {
            return user;
        }
        public void setUser(User user) {
            this.user = user;
        }
    public long getIdProfesor() {
        return idProfesor;
    }
    public void setIdProfesor(long idProfesor) {
        this.idProfesor = idProfesor;
    }
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Integer getTelefono() {
        return telefono;
    }
}
