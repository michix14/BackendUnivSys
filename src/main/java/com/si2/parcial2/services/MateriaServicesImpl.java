package com.si2.parcial2.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.Materia;
import com.si2.parcial2.repositories.MateriaRepository;

@Service
public class MateriaServicesImpl implements MateriaServices {

    @Autowired
    private MateriaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Materia> findAll() {
        return (List<Materia>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Materia> findById(Long id) {

        return repository.findById(id);
    }

    @Override
    @Transactional
    public Materia save(Materia m) {
        return repository.save(m);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findMateriasByProfesorId(Long profesorId) {
        return repository.findMateriasByProfesorId(profesorId);
    }

}
