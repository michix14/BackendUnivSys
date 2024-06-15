package com.si2.parcial2.services;
import java.util.List;

import java.util.Optional;
import java.time.LocalTime;

import com.si2.parcial2.entities.Horario;


public interface HorarioServices {
    
    List<Horario> findAll();
    Optional<Horario> findById(Long id);
    Horario save(Horario h);
    void deleteById(Long id);
    boolean existsByDiaAndHoraAndAula(String dia, LocalTime horaInicio, LocalTime horaFin, Long aulaId);
    boolean existsByDiaAndHoraAndProfesor(String dia, LocalTime horaInicio, LocalTime horaFin, Long profesorId);

}
