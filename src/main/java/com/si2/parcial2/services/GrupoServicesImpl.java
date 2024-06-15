package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.Grupo;
import com.si2.parcial2.repositories.GrupoRepository;

@Service
public class GrupoServicesImpl implements GrupoServices {

    @Autowired
    private GrupoRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Grupo> findAll() {
        return (List<Grupo>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Grupo> findById(Long id) {

        return repository.findById(id);
    }

    @Override
    @Transactional
    public Grupo save(Grupo g) {
        return repository.save(g);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
}
