package com.si2.parcial2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.si2.parcial2.entities.Grupo;
import com.si2.parcial2.entities.Carrera;
import com.si2.parcial2.entities.Materia;
import com.si2.parcial2.entities.profesor;
import com.si2.parcial2.services.GrupoServices;
import com.si2.parcial2.services.CarreraServices;
import com.si2.parcial2.services.MateriaServices;
import com.si2.parcial2.services.ProfesorServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoServices grupoServices;

    @Autowired
    private CarreraServices carreraServices;

    @Autowired
    private MateriaServices materiaServices;

    @Autowired
    private ProfesorServices profesorServices;

    @GetMapping
    public ResponseEntity<List<Grupo>> getAllGrupos() {
        List<Grupo> grupos = grupoServices.findAll();
        return new ResponseEntity<>(grupos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable Long id) {
        Optional<Grupo> grupo = grupoServices.findById(id);
        return grupo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Grupo save(Grupo g) {
        return grupoServices.save(g);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Grupo g) {
        return ResponseEntity.status(HttpStatus.CREATED).body(save(g));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable Long id, @RequestBody Grupo grupoDetails) {
        Optional<Grupo> grupo = grupoServices.findById(id);
        if (!grupo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Carrera> carrera = carreraServices.findById(grupoDetails.getCarrera().getId());
        if (!carrera.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Materia> materia = materiaServices.findById(grupoDetails.getMateria().getId());
        if (!materia.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<profesor> profesor = profesorServices.findById(grupoDetails.getProfesor().getIdProfesor());
        if (!profesor.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Grupo updatedGrupo = grupo.get();
        updatedGrupo.setNombre(grupoDetails.getNombre());
        updatedGrupo.setCarrera(carrera.get());
        updatedGrupo.setMateria(materia.get());
        updatedGrupo.setProfesor(profesor.get());
        grupoServices.save(updatedGrupo);

        return new ResponseEntity<>(updatedGrupo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        Optional<Grupo> grupo = grupoServices.findById(id);
        if (!grupo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        grupoServices.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
