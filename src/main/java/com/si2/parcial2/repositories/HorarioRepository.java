package com.si2.parcial2.repositories;

import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.si2.parcial2.entities.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Long>{
        List<Horario> findByDiaAndHoraInicioAndHoraFinAndAulaId(String dia, LocalTime horaInicio, LocalTime horaFin, Long aulaId);
            @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END FROM Horario h " +
           "WHERE h.dia = :dia AND h.grupo.profesor.id = :profesorId " +
           "AND ((:horaInicio BETWEEN h.horaInicio AND h.horaFin) OR " +
                "(:horaFin BETWEEN h.horaInicio AND h.horaFin) OR " +
                "(h.horaInicio BETWEEN :horaInicio AND :horaFin))")
    boolean existsByDiaAndHoraAndProfesor(@Param("dia") String dia, 
                                          @Param("horaInicio") LocalTime horaInicio, 
                                          @Param("horaFin") LocalTime horaFin, 
                                          @Param("profesorId") Long profesorId);
}
