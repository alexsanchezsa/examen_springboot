package com.examen.sanchezperalta.service;

import com.examen.sanchezperalta.entity.Habilidad;
import java.util.List;
import java.util.Optional;

public interface HabilidadService {
    List<Habilidad> findAll();
    Optional<Habilidad> findById(Long id);
    Habilidad save(Habilidad habilidad);
    void deleteById(Long id);
}
