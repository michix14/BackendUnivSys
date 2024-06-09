package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.Facultad;

import com.si2.parcial2.repositories.FacultadRepository;

@Service
public class FacultadServicesImpl implements FacultadServices {

   
    @Autowired
    private FacultadRepository repository;
    @Transactional(readOnly = true)
    @Override
    public List<Facultad> findAll() {

        return (List<Facultad>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Facultad> findById(Long id) {
  
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Facultad save(Facultad f) {
        return repository.save(f);
    } 
    
    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
