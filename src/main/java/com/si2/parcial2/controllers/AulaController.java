package com.si2.parcial2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.si2.parcial2.entities.Aula;
import com.si2.parcial2.entities.Modulo;
import com.si2.parcial2.services.AulaServices;
import com.si2.parcial2.services.ModuloServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/aulas")
public class AulaController {

    @Autowired
    private AulaServices aulaServices;

    @Autowired
    private ModuloServices moduloServices;

    @GetMapping
    public ResponseEntity<List<Aula>> getAllAulas() {
        List<Aula> aulas = aulaServices.findAll();
        return new ResponseEntity<>(aulas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aula> getAulaById(@PathVariable Long id) {
        Optional<Aula> aula = aulaServices.findById(id);
        return aula.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Aula save(Aula a) {
        return aulaServices.save(a);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Aula a) {
        return ResponseEntity.status(HttpStatus.CREATED).body(save(a));
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Aula> updateAula(@PathVariable Long id, @RequestBody Aula aulaDetails) {
        Optional<Aula> aula = aulaServices.findById(id);
        if (!aula.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Modulo> modulo = moduloServices.findById(aulaDetails.getModulo().getId());
        if (!modulo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Aula updatedAula = aula.get();
        updatedAula.setNumero(aulaDetails.getNumero());
        updatedAula.setTipo(aulaDetails.getTipo());
        updatedAula.setCapacidad(aulaDetails.getCapacidad());
        updatedAula.setModulo(modulo.get());
        aulaServices.save(updatedAula);
        return new ResponseEntity<>(updatedAula, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        Optional<Aula> aula = aulaServices.findById(id);
        if (!aula.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        aulaServices.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
