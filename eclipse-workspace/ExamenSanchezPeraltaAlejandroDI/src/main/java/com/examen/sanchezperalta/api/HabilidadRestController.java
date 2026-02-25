package com.examen.sanchezperalta.api;

import com.examen.sanchezperalta.entity.Habilidad;
import com.examen.sanchezperalta.service.HabilidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habilidades")
public class HabilidadRestController {

    @Autowired
    private HabilidadService habilidadService;

    // GET /api/habilidades → devuelve todas las habilidades (ejercicio 3)
    @GetMapping
    public List<Habilidad> listar() {
        return habilidadService.findAll();
    }

    // GET /api/habilidades/{id} → devuelve una habilidad por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Habilidad> getById(@PathVariable Long id) {
        return habilidadService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/habilidades → crea una nueva habilidad y retorna HTTP 200 (ejercicio 4)
    @PostMapping
    public ResponseEntity<Habilidad> crear(@Valid @RequestBody Habilidad habilidad) {
        return ResponseEntity.ok(habilidadService.save(habilidad));
    }

    // PUT /api/habilidades/{id} → actualiza una habilidad existente (ejercicio 4)
    @PutMapping("/{id}")
    public ResponseEntity<Habilidad> actualizar(@PathVariable Long id,
                                                @Valid @RequestBody Habilidad habilidad) {
        if (habilidadService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        habilidad.setId(id);
        return ResponseEntity.ok(habilidadService.save(habilidad));
    }

    // DELETE /api/habilidades/{id} → elimina una habilidad por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (habilidadService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        habilidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
