package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.profesor;
import com.si2.parcial2.repositories.ProfesorRepository;

@Service
public class ProfesorServicesImpl implements ProfesorServices {
   
    @Autowired
    private ProfesorRepository repository;
    @Transactional(readOnly = true)
    @Override
    public List<profesor> findAll() {

        return (List<profesor>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<profesor> findById(Long idProfesor) {
  
        return repository.findById(idProfesor);
    }

    @Override
    @Transactional
    public profesor save(profesor p) {
        return repository.save(p);
    } 
    
    @Transactional
    @Override
    public void deleteById(Long idProfesor) {
        repository.deleteById(idProfesor);
    }
    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        return repository.existsBySku(sku);
    }

}
