package com.si2.parcial2.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.Asistencia;
import com.si2.parcial2.repositories.AsistenciaRepository;


@Service
public class AsistenciaServicesImpl implements AsistenciaServices{
    @Autowired
    private AsistenciaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Asistencia> findAll() {
        return (List<Asistencia>) repository.findAll();
    }

        @Override
    @Transactional(readOnly = true)
    public Optional<Asistencia> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Asistencia save(Asistencia a) {
        return repository.save(a);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Asistencia> findByGrupoAndFecha(Long grupoId, LocalDate fecha) {
        return repository.findByGrupoIdAndFecha(grupoId, fecha);
    }

}
