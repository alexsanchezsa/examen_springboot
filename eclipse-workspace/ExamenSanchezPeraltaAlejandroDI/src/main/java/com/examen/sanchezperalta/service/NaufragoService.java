package com.examen.sanchezperalta.service;

import com.examen.sanchezperalta.entity.Naufrago;
import java.util.List;
import java.util.Optional;

public interface NaufragoService {
    List<Naufrago> findAll();
    Optional<Naufrago> findById(Long id);
    Naufrago save(Naufrago naufrago);
    void deleteById(Long id);
}
