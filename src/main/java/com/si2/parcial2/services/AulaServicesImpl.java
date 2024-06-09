package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.Aula;
import com.si2.parcial2.repositories.AulaRepository;

@Service
public class AulaServicesImpl implements AulaServices {

    @Autowired
    private AulaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Aula> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Aula> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Aula save(Aula a) {
        return repository.save(a);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
