package com.si2.parcial2.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.si2.parcial2.entities.Grupo;
import com.si2.parcial2.entities.Materia;
import com.si2.parcial2.entities.User;
import com.si2.parcial2.entities.profesor;
import com.si2.parcial2.projection.MateriaInfo;
import com.si2.parcial2.services.ProfesorServices;
import com.si2.parcial2.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "*", originPatterns = "*")
@RestController
@RequestMapping("/api/profesor")
public class ProfesorController {
    @Autowired
    private ProfesorServices service;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<profesor> list() {
        return service.findAll();
    }

    @GetMapping("/materias")
    public ResponseEntity<List<MateriaInfo>> getMateriasByLoggedProfesor() {
        String username = getCurrentUsername();
        profesor profesor = service.findByUserUsername(username);
        Long idProfesor = profesor.getIdProfesor();

        List<MateriaInfo> materiasInfo = service.findMateriasByProfesorId(idProfesor);

        return ResponseEntity.ok(materiasInfo);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping("/{idProfesor}")
    public ResponseEntity<?> view(@PathVariable Long idProfesor) {

        Optional<profesor> profOptional = service.findById(idProfesor);
        if (profOptional.isPresent()) {
            return ResponseEntity.ok(profOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    public profesor save(profesor p) {
        return service.save(p);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody profesor p) {
        return ResponseEntity.status(HttpStatus.CREATED).body(save(p));
    }

    @PutMapping("/{idProfesor}")
    public ResponseEntity<?> update(@RequestBody profesor profesor, @PathVariable Long idProfesor) {
        Optional<profesor> profOptional = service.findById(idProfesor);
        if (profOptional.isPresent()) {
            profesor currentProfesor = profOptional.get();
            currentProfesor.setNombre(profesor.getNombre());
            currentProfesor.setDireccion(profesor.getDireccion());
            currentProfesor.setTelefono(profesor.getTelefono());
            profesor updatedProfesor = service.save(currentProfesor);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedProfesor);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idProfesor}")
    public ResponseEntity<?> delete(@PathVariable Long idProfesor) {
        Optional<profesor> profOptional = service.findById(idProfesor);
        if (profOptional.isPresent()) {
            service.deleteById(idProfesor);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerProfesorWithUser(@Valid @RequestBody profesor p, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors");
        }

        try {
            // Registrar el usuario
            User createdUser = userService.save(p.getUser());
            // Registrar el profesor con el usuario creado
            p.setUser(createdUser);
            profesor savedProfesor = service.save(p);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfesor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
