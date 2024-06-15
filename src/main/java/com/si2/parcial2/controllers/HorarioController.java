package com.si2.parcial2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.si2.parcial2.entities.Horario;
import com.si2.parcial2.entities.Aula;
import com.si2.parcial2.entities.Grupo;
import com.si2.parcial2.services.HorarioServices;
import com.si2.parcial2.services.AulaServices;
import com.si2.parcial2.services.GrupoServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioServices horarioServices;

    @Autowired
    private AulaServices aulaServices;

    @Autowired
    private GrupoServices grupoServices;

    @GetMapping
    public ResponseEntity<List<Horario>> getAllHorarios() {
        List<Horario> horarios = horarioServices.findAll();
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Horario> getHorarioById(@PathVariable Long id) {
        Optional<Horario> horario = horarioServices.findById(id);
        return horario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Horario horario) {
        Optional<Aula> aula = aulaServices.findById(horario.getAula().getId());
        Optional<Grupo> grupo = grupoServices.findById(horario.getGrupo().getId());

        if (!aula.isPresent() || !grupo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean existsAula = horarioServices.existsByDiaAndHoraAndAula(horario.getDia(), horario.getHoraInicio(), horario.getHoraFin(), horario.getAula().getId());
        if (existsAula) {
            return new ResponseEntity<>("Ya existe un horario con el mismo día y hora para esta aula", HttpStatus.CONFLICT);
        }

        boolean existsProfesor = horarioServices.existsByDiaAndHoraAndProfesor(horario.getDia(), horario.getHoraInicio(), horario.getHoraFin(), grupo.get().getProfesor().getIdProfesor());
        if (existsProfesor) {
            return new ResponseEntity<>("El profesor ya tiene un horario asignado en este intervalo de tiempo", HttpStatus.CONFLICT);
        }

        horario.setAula(aula.get());
        horario.setGrupo(grupo.get());
        Horario savedHorario = horarioServices.save(horario);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedHorario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Horario> updateHorario(@PathVariable Long id, @RequestBody Horario horarioDetails) {
        Optional<Horario> horario = horarioServices.findById(id);
        if (!horario.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Aula> aula = aulaServices.findById(horarioDetails.getAula().getId());
        Optional<Grupo> grupo = grupoServices.findById(horarioDetails.getGrupo().getId());

        if (!aula.isPresent() || !grupo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Horario updatedHorario = horario.get();
        updatedHorario.setDia(horarioDetails.getDia());
        updatedHorario.setHoraInicio(horarioDetails.getHoraInicio());
        updatedHorario.setHoraFin(horarioDetails.getHoraFin());
        updatedHorario.setAula(aula.get());
        updatedHorario.setGrupo(grupo.get());

        horarioServices.save(updatedHorario);

        return new ResponseEntity<>(updatedHorario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        Optional<Horario> horario = horarioServices.findById(id);
        if (!horario.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        horarioServices.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
