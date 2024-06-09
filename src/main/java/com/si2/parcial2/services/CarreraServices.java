package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;



import com.si2.parcial2.entities.Carrera;

public interface CarreraServices {
List<Carrera> findAll();
Optional<Carrera> findById(Long id);
Carrera save(Carrera a);
void deleteById(Long id);
}