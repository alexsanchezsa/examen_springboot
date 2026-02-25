package com.examen.sanchezperalta.service.impl;

import com.examen.sanchezperalta.entity.Habilidad;
import com.examen.sanchezperalta.repository.HabilidadRepository;
import com.examen.sanchezperalta.service.HabilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HabilidadServiceImpl implements HabilidadService {

    @Autowired
    private HabilidadRepository repo;

    @Override public List<Habilidad> findAll()              { return repo.findAll(); }
    @Override public Optional<Habilidad> findById(Long id)  { return repo.findById(id); }
    @Override public Habilidad save(Habilidad h)             { return repo.save(h); }
    @Override public void deleteById(Long id)                { repo.deleteById(id); }
}
