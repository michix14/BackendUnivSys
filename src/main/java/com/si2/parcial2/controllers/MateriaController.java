package com.si2.parcial2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.si2.parcial2.entities.Materia;
import com.si2.parcial2.services.MateriaServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/materia")
public class MateriaController {

    @Autowired
    private MateriaServices service;

    @GetMapping
    public List<Materia> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Materia> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            return ResponseEntity.ok(modOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    public Materia save(Materia m) {
        return service.save(m);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Materia m) {
        return ResponseEntity.status(HttpStatus.CREATED).body(save(m));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Materia Materia, @PathVariable Long id) {
        Optional<Materia> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            Materia currentMateria = modOptional.get();
            currentMateria.setNombre(Materia.getNombre());
            currentMateria.setSemestre(Materia.getSemestre());
            currentMateria.setSigla(Materia.getSigla());
            Materia updatedMateria = service.save(currentMateria);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedMateria);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Materia> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
