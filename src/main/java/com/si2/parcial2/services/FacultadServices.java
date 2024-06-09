package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import com.si2.parcial2.entities.Facultad;

public interface FacultadServices {
    
    List<Facultad> findAll();
    Optional<Facultad> findById(Long id);
    Facultad save(Facultad m);
    void deleteById(Long id);

}
