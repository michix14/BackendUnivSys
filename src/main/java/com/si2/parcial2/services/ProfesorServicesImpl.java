package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.si2.parcial2.entities.User;
import com.si2.parcial2.entities.profesor;
import com.si2.parcial2.projection.MateriaInfo;
import com.si2.parcial2.repositories.ProfesorRepository;
import com.si2.parcial2.repositories.UserRepository;

@Service
public class ProfesorServicesImpl implements ProfesorServices {
   
    @Autowired
    private ProfesorRepository repository;
    @Autowired
    private UserRepository userRepository;
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

    @Transactional
    @Override
    public profesor registerProfesorWithUser(profesor profesor, User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        User savedUser = userRepository.save(user);
        profesor.setUser(savedUser);
        return repository.save(profesor);
    }

    public profesor findByUserUsername(String username) {
        return repository.findByUserUsername(username);
    }

        public List<MateriaInfo> findMateriasByProfesorId(Long idProfesor) {
        return repository.findMateriasByProfesorId(idProfesor);
    }
}
