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

import com.si2.parcial2.entities.Asistencia;
import com.si2.parcial2.services.AsistenciaServices;

@CrossOrigin(origins = "*", originPatterns = "*")
@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaServices asistenciaServices;

    @GetMapping
    public ResponseEntity<List<Asistencia>> list() {
        List<Asistencia> asistencias = asistenciaServices.findAll();
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> view(@PathVariable Long id) {
        Optional<Asistencia> asistenciaOptional = asistenciaServices.findById(id);
        if (asistenciaOptional.isPresent()) {
            return ResponseEntity.ok(asistenciaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Asistencia> create(@RequestBody Asistencia asistencia) {
        Asistencia savedAsistencia = asistenciaServices.save(asistencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAsistencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> update(@RequestBody Asistencia asistencia, @PathVariable Long id) {
        Optional<Asistencia> asistenciaOptional = asistenciaServices.findById(id);
        if (asistenciaOptional.isPresent()) {
            Asistencia currentAsistencia = asistenciaOptional.get();
            currentAsistencia.setHora(asistencia.getHora());
            currentAsistencia.setEstado(asistencia.getEstado());
            currentAsistencia.setFecha(asistencia.getFecha());
            currentAsistencia.setGrupo(asistencia.getGrupo());
            Asistencia updatedAsistencia = asistenciaServices.save(currentAsistencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedAsistencia);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Asistencia> asistenciaOptional = asistenciaServices.findById(id);
        if (asistenciaOptional.isPresent()) {
            asistenciaServices.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
