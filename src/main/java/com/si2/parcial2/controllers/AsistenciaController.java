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
    public ResponseEntity<?> create(@RequestBody Asistencia asistencia, @RequestParam Double latitud, @RequestParam Double longitud) {
        Optional<Grupo> optionalGrupo = grupoServices.findById(asistencia.getGrupo().getId());
        
        if (!optionalGrupo.isPresent()) {
            return new ResponseEntity<>("Grupo no encontrado", HttpStatus.BAD_REQUEST);
        }

        Grupo grupo = optionalGrupo.get();
        if (grupo.getHorarios().isEmpty()) {
            return new ResponseEntity<>("Horarios no encontrados para el grupo", HttpStatus.BAD_REQUEST);
        }

        boolean valid = false;

        // Obtener la hora y el día actuales del sistema
        LocalTime currentTime = LocalTime.now();
        String currentDay = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

        for (Horario horario : grupo.getHorarios()) {
            // Validar el día en español
            if (horario.getDia().equalsIgnoreCase(currentDay)) {
                // Validar la hora
                LocalTime startTime = horario.getHoraInicio();
                if (currentTime.isAfter(startTime.minusMinutes(10)) && currentTime.isBefore(horario.getHoraFin().plusMinutes(10))) {
                    // Validar la ubicación
                    double distance = distance(horario.getAula().getModulo().getLatitud(), horario.getAula().getModulo().getLongitud(), latitud, longitud);
                    if (distance <= 100) {
                        valid = true;
                        break;
                    }
                }
            }
        }

        if (!valid) {
            return new ResponseEntity<>("No se cumplen las condiciones de hora, día y ubicación", HttpStatus.BAD_REQUEST);
        }

        Asistencia savedAsistencia = asistenciaServices.save(asistencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAsistencia);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // Fórmula de Haversine para calcular la distancia entre dos puntos
        final int R = 6371; // Radio de la Tierra en kilómetros

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // Convertir a metros

        return distance;
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
