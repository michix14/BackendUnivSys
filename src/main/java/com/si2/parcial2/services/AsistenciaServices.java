package com.si2.parcial2.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.si2.parcial2.entities.Asistencia;

public interface AsistenciaServices {
    List<Asistencia> findAll();
    Optional<Asistencia> findById(Long id);
    Asistencia save(Asistencia a);
    void deleteById(Long id);
    Optional<Asistencia> findByGrupoAndFecha(Long grupoId, LocalDate fecha);

}
