package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.Modulo;

import com.si2.parcial2.repositories.ModuloRepository;

@Service
public class ModuloServicesImpl implements ModuloServices {

   
    @Autowired
    private ModuloRepository repository;
    @Transactional(readOnly = true)
    @Override
    public List<Modulo> findAll() {

        return (List<Modulo>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Modulo> findById(Long id) {
  
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Modulo save(Modulo m) {
        return repository.save(m);
    } 
    
    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
