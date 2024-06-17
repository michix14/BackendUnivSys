package com.si2.parcial2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.si2.parcial2.entities.profesor;
import com.si2.parcial2.projection.MateriaInfo;

public interface ProfesorRepository extends JpaRepository<profesor, Long>{
    profesor findByUserUsername(String username);
        @Query(value = "SELECT m.nombre as materiaNombre, g.nombre as grupoNombre,g.id as idGrupo , h.dia, h.hora_inicio as horaInicio, h.hora_fin as horaFin, " +
                   "mo.numero as numeroModulo, a.numero as numeroAula, a.tipo as tipoAula " +
                   "FROM grupos g " +
                   "JOIN profesor p ON g.profesor_id = p.id_profesor " +
                   "JOIN materias m ON g.materia_id = m.id " +
                   "JOIN horarios h ON g.id = h.grupo_id " +
                   "JOIN aulas a ON h.aula_id = a.id " +
                   "JOIN modulos mo ON a.id_modulo = mo.id " +
                   "WHERE p.id_profesor = :idProfesor", nativeQuery = true)
    List<MateriaInfo> findMateriasByProfesorId(@Param("idProfesor") Long idProfesor);
   
} 