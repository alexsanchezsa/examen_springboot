package com.examen.sanchezperalta.api;

import com.examen.sanchezperalta.entity.Habilidad;
import com.examen.sanchezperalta.service.HabilidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/habilidades")
public class HabilidadRestController {

    @Autowired
    private HabilidadService habilidadService;

    @GetMapping
    public List<Habilidad> listar() {
        return habilidadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habilidad> getById(@PathVariable Long id) {
        return habilidadService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Habilidad crear(@Valid @RequestBody Habilidad habilidad) {
        return habilidadService.save(habilidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habilidad> actualizar(@PathVariable Long id,
                                                @Valid @RequestBody Habilidad habilidad) {
        if (habilidadService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        habilidad.setId(id);
        return ResponseEntity.ok(habilidadService.save(habilidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (habilidadService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        habilidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
