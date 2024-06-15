package com.si2.parcial2.repositories;

import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.si2.parcial2.entities.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Long>{
        List<Horario> findByDiaAndHoraInicioAndHoraFinAndAulaId(String dia, LocalTime horaInicio, LocalTime horaFin, Long aulaId);
}
