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

import com.si2.parcial2.entities.Facultad;
import com.si2.parcial2.services.FacultadServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/facultad")
public class FacultadController {

    @Autowired
    private FacultadServices service;

    @GetMapping
    public List<Facultad> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Facultad> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            return ResponseEntity.ok(modOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    public Facultad save(Facultad m) {
        return service.save(m);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Facultad m) {
        return ResponseEntity.status(HttpStatus.CREATED).body(save(m));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Facultad Facultad, @PathVariable Long id) {
        Optional<Facultad> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            Facultad currentFacultad = modOptional.get();
            currentFacultad.setDescripcion(Facultad.getDescripcion());
            currentFacultad.setNombre(Facultad.getNombre());
            Facultad updatedFacultad = service.save(currentFacultad);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedFacultad);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Facultad> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
