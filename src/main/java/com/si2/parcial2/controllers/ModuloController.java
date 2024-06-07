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

import com.si2.parcial2.entities.Modulo;
import com.si2.parcial2.services.ModuloServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/modulo")
public class ModuloController {

    @Autowired
    private ModuloServices service;

    @GetMapping
    public List<Modulo> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Modulo> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            return ResponseEntity.ok(modOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    public Modulo save(Modulo m) {
        return service.save(m);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Modulo m) {
        return ResponseEntity.status(HttpStatus.CREATED).body(save(m));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Modulo modulo, @PathVariable Long id) {
        Optional<Modulo> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            Modulo currentModulo = modOptional.get();
            currentModulo.setNumero(modulo.getNumero());
            currentModulo.setLatitud(modulo.getLatitud());
            currentModulo.setLongitud(modulo.getLongitud());
            Modulo updatedModulo = service.save(currentModulo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedModulo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Modulo> modOptional = service.findById(id);
        if (modOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
