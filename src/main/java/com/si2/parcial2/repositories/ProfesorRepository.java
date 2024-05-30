package com.si2.parcial2.repositories;

import org.springframework.data.repository.CrudRepository;
import com.si2.parcial2.entities.profesor;

public interface ProfesorRepository extends CrudRepository<profesor, Long>{
    boolean existsBySku(String sku);
} 