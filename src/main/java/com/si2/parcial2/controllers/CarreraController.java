package com.si2.parcial2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.si2.parcial2.entities.Carrera;
import com.si2.parcial2.entities.Facultad;

import com.si2.parcial2.services.CarreraServices;
import com.si2.parcial2.services.FacultadServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/carreras")
public class CarreraController {

    @Autowired
    private CarreraServices CarreraServices;

    @Autowired
    private FacultadServices facultadServices;

    @GetMapping
    public ResponseEntity<List<Carrera>> getAllCarreras() {
        List<Carrera> Carreras = CarreraServices.findAll();
        return new ResponseEntity<>(Carreras, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrera> getCarreraById(@PathVariable Long id) {
        Optional<Carrera> Carrera = CarreraServices.findById(id);
        return Carrera.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Carrera save(Carrera c) {
        return CarreraServices.save(c);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Carrera c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(save(c));
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Carrera> updateCarrera(@PathVariable Long id, @RequestBody Carrera CarreraDetails) {
        Optional<Carrera> Carrera = CarreraServices.findById(id);
        if (!Carrera.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Facultad> facultad = facultadServices.findById(CarreraDetails.getFacultad().getId());
        if (!facultad.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Carrera updatedCarrera = Carrera.get();
        updatedCarrera.setCodigo(CarreraDetails.getCodigo());;
        updatedCarrera.setNombre(CarreraDetails.getNombre());;
        updatedCarrera.setFacultad(facultad.get());
        CarreraServices.save(updatedCarrera);
        return new ResponseEntity<>(updatedCarrera, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrera(@PathVariable Long id) {
        Optional<Carrera> Carrera = CarreraServices.findById(id);
        if (!Carrera.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CarreraServices.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
