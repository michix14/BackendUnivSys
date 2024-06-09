package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.Carrera;
import com.si2.parcial2.repositories.CarreraRepository;

@Service
public class CarreraServicesImpl implements CarreraServices {

    @Autowired
    private CarreraRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Carrera> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Carrera> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Carrera save(Carrera c) {
        return repository.save(c);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
