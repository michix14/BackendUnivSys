package com.si2.parcial2.entities;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "grupos")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "carrera_id", referencedColumnName = "id")
    private Carrera carrera;
    public Carrera getCarrera() {
        return carrera;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "grupo")
    private List<Horario> horarios;
    
    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @ManyToOne
    @JoinColumn(name = "materia_id", referencedColumnName = "id")
    private Materia materia;
    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }



    @ManyToOne
    @JoinColumn(name = "profesor_id", referencedColumnName = "idProfesor")
    private profesor profesor;


    public profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(profesor profesor) {
        this.profesor = profesor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
