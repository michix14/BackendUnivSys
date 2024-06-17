package com.si2.parcial2.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.si2.parcial2.entities.Asistencia;
import com.si2.parcial2.entities.Grupo;
import com.si2.parcial2.entities.Horario;
import com.si2.parcial2.services.AsistenciaServices;
import com.si2.parcial2.services.GrupoServices;

@CrossOrigin(origins = "*", originPatterns = "*")
@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaServices asistenciaServices;
    @Autowired
    private GrupoServices grupoServices;

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
    public ResponseEntity<?> create(@RequestBody Asistencia asistencia, @RequestParam Float latitud, @RequestParam Float longitud) {
        Optional<Grupo> optionalGrupo = grupoServices.findById(asistencia.getGrupo().getId());
    
        if (!optionalGrupo.isPresent()) {
            return new ResponseEntity<>("Grupo no encontrado", HttpStatus.BAD_REQUEST);
        }
    
        Grupo grupo = optionalGrupo.get();
        if (grupo.getHorarios().isEmpty()) {
            return new ResponseEntity<>("Horarios no encontrados para el grupo", HttpStatus.BAD_REQUEST);
        }
    
        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
    
        // Verificar si ya existe una asistencia registrada para este grupo en la fecha actual
        Optional<Asistencia> existingAsistencia = asistenciaServices.findByGrupoAndFecha(grupo.getId(), currentDate);
        if (existingAsistencia.isPresent()) {
            return new ResponseEntity<>("Ya existe una asistencia registrada para este grupo en la fecha actual", HttpStatus.BAD_REQUEST);
        }
    
        // Obtener la hora actual del sistema
        LocalTime currentTime = LocalTime.now();
        String currentDay = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
    
        for (Horario horario : grupo.getHorarios()) {
            // Validar el día en español
            if (horario.getDia().equalsIgnoreCase(currentDay)) {
                // Validar la hora
                LocalTime startTime = horario.getHoraInicio();
                if (currentTime.isAfter(startTime.minusMinutes(10)) && currentTime.isBefore(horario.getHoraFin().plusMinutes(10))) {
                    // Validar la ubicación
                    float distance = distance(horario.getAula().getModulo().getLatitud(), horario.getAula().getModulo().getLongitud(), latitud, longitud);
                    if (distance <= 50) { // Validar en un radio de 50 metros (0.05 km)
                        // Verificar si han pasado más de 20 minutos desde la hora de inicio
                        if (currentTime.isAfter(startTime.plusMinutes(20))) {
                            return new ResponseEntity<>("No se cumplen las condiciones de hora, día o ubicación válida en 50 metros", HttpStatus.BAD_REQUEST); // No registrar la asistencia si han pasado más de 20 minutos desde la hora de inicio
                        } else if (currentTime.isAfter(startTime.plusMinutes(10))) {
                            asistencia.setEstado("Atrasado");
                        } else {
                            asistencia.setEstado("Presente");
                        }
                        // Guardar la asistencia
                        asistencia.setFecha(currentDate);
                        Asistencia savedAsistencia = asistenciaServices.save(asistencia);
                        return ResponseEntity.status(HttpStatus.CREATED).body(savedAsistencia);
                    }
                }
            }
        }
    
        return new ResponseEntity<>("No se cumplen las condiciones de hora, día o ubicación válida en 50 metros", HttpStatus.BAD_REQUEST);
    }
    

    private float distance(float lat1, float lon1, float lat2, float lon2) {
        // Radio de la Tierra en kilómetros
        final float R = 6371.0f; // Radio de la Tierra en kilómetros

        // Convertir coordenadas a radianes
        float latDistance = (float) Math.toRadians(lat2 - lat1);
        float lonDistance = (float) Math.toRadians(lon2 - lon1);

        // Aplicar la fórmula de Haversine
        float a = (float) (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
        float c = 2 * (float) Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float distance = R * c * 1000; // Convertir a metros

        return distance;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createLicencia(@RequestBody Asistencia asistencia) {
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
