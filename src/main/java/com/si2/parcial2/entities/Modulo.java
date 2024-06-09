package com.si2.parcial2.entities;





import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "modulos")
public class Modulo {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

 @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
private Integer numero;
public Integer getNumero() {
    return numero;
}
public void setNumero(Integer numero) {
    this.numero = numero;
}
private float latitud;

public float getLatitud() {
    return latitud;
}
public void setLatitud(float latitud) {
    this.latitud = latitud;
}
private float longitud;
public float getLongitud() {
    return longitud;
}
public void setLongitud(float longitud) {
    this.longitud = longitud;
}

}
