package com.examen.sanchezperalta.service.impl;

import com.examen.sanchezperalta.entity.Naufrago;
import com.examen.sanchezperalta.repository.NaufragoRepository;
import com.examen.sanchezperalta.service.NaufragoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NaufragoServiceImpl implements NaufragoService {

    @Autowired
    private NaufragoRepository repo;

    @Override public List<Naufrago> findAll()              { return repo.findAll(); }
    @Override public Optional<Naufrago> findById(Long id)  { return repo.findById(id); }
    @Override public Naufrago save(Naufrago n)              { return repo.save(n); }
    @Override public void deleteById(Long id)               { repo.deleteById(id); }
}
