package com.si2.parcial2.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.si2.parcial2.entities.Materia;

public interface MateriaServices {
    List<Materia> findAll();

    Optional<Materia> findById(Long id);

    Materia save(Materia m);

    void deleteById(Long id);
    List<Map<String, Object>> findMateriasByProfesorId(Long profesorId);

}
