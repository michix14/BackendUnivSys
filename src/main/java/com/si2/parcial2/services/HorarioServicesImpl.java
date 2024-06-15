package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.Horario;
import com.si2.parcial2.repositories.HorarioRepository;
import java.time.LocalTime; 

@Service
public class HorarioServicesImpl implements HorarioServices{
    
    @Autowired
    private HorarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Horario> findAll() {
        return (List<Horario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> findById(Long id) {

        return repository.findById(id);
    }

    @Override
    @Transactional
    public Horario save(Horario h) {
        return repository.save(h);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByDiaAndHoraAndAula(String dia, LocalTime horaInicio, LocalTime horaFin, Long aulaId) {
        List<Horario> horarios = repository.findByDiaAndHoraInicioAndHoraFinAndAulaId(dia, horaInicio, horaFin, aulaId);
        return !horarios.isEmpty();
    }
    @Transactional(readOnly = true)
    @Override
    public boolean existsByDiaAndHoraAndProfesor(String dia, LocalTime horaInicio, LocalTime horaFin, Long profesorId) {
        return repository.existsByDiaAndHoraAndProfesor(dia, horaInicio, horaFin, profesorId);
    }

}
