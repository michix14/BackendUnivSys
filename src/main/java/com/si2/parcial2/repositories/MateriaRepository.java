package com.si2.parcial2.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.si2.parcial2.entities.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long>{
    @Query(value = "SELECT m.*, h.dia, h.hora_inicio, h.hora_fin, a.numero as numero_aula, a.tipo as tipo_aula, mo.numero as numero_modulo " +
                   "FROM profesor p " +
                   "JOIN grupos g ON p.id_profesor = g.profesor_id " +
                   "JOIN materias m ON g.materia_id = m.id " +
                   "JOIN horarios h ON g.id = h.grupo_id " +
                   "JOIN aulas a ON h.aula_id = a.id " +
                   "JOIN modulos mo ON a.id_modulo = mo.id " +
                   "WHERE p.id_profesor = :profesorId", nativeQuery = true)
    List<Map<String, Object>> findMateriasByProfesorId(@Param("profesorId") Long profesorId);
}
