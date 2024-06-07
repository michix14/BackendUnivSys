package com.si2.parcial2.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aulas")
public class Aula {
    @Id
    @Generated(value = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Long id;
    private Integer numero;
    private String tipo;
    private Integer capacidad;

    @ManyToOne 
    @JoinColumn(name = "idModulo", nullable = false) 
    private Modulo modulo;

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdModulo() {
        return modulo != null ? modulo.getId() : null;
    }
}
