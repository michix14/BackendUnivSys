package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import com.si2.parcial2.entities.Grupo;

public interface GrupoServices {
    List<Grupo> findAll();
    Optional<Grupo> findById(Long id);
    Grupo save(Grupo g);
    void deleteById(Long id);
    
}
