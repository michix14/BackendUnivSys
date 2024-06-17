package com.si2.parcial2.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.si2.parcial2.entities.Asistencia;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long>{
    Optional<Asistencia> findByGrupoIdAndFecha(Long grupoId, LocalDate fecha);

}
