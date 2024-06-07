package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;



import com.si2.parcial2.entities.Aula;

public interface AulaServices {
List<Aula> findAll();
Optional<Aula> findById(Long id);
Aula save(Aula a);
void deleteById(Long id);
}
