package com.si2.parcial2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.si2.parcial2.entities.profesor;

public interface ProfesorRepository extends JpaRepository<profesor, Long>{
    profesor findByUserUsername(String username);
   
} 