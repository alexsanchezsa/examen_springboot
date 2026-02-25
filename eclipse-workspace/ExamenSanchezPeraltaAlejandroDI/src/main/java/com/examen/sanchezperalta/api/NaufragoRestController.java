package com.examen.sanchezperalta.api;

import com.examen.sanchezperalta.entity.Habilidad;
import com.examen.sanchezperalta.entity.Naufrago;
import com.examen.sanchezperalta.service.HabilidadService;
import com.examen.sanchezperalta.service.NaufragoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/naufragos")
public class NaufragoRestController {

    @Autowired
    private NaufragoService naufragoService;

    @Autowired
    private HabilidadService habilidadService;

    // GET /api/naufragos → devuelve todos los náufragos (ejercicio 3)
    @GetMapping
    public List<Naufrago> listar() {
        return naufragoService.findAll();
    }

    // GET /api/naufragos/{id} → devuelve un náufrago por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Naufrago> getById(@PathVariable Long id) {
        return naufragoService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/naufragos → crea un nuevo náufrago y retorna HTTP 200 (ejercicio 4)
    // Las habilidades del cuerpo JSON se resuelven por ID para obtener entidades gestionadas
    @PostMapping
    public ResponseEntity<Naufrago> crear(@Valid @RequestBody Naufrago naufrago) {
        if (naufrago.getHabilidades() != null && !naufrago.getHabilidades().isEmpty()) {
            List<Habilidad> gestionadas = new ArrayList<>();
            for (Habilidad h : naufrago.getHabilidades()) {
                habilidadService.findById(h.getId()).ifPresent(gestionadas::add);
            }
            naufrago.setHabilidades(gestionadas);
        }
        return ResponseEntity.ok(naufragoService.save(naufrago));
    }

    // PUT /api/naufragos/{id} → modifica los datos de un náufrago existente (ejercicio 4)
    @PutMapping("/{id}")
    public ResponseEntity<Naufrago> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody Naufrago naufrago) {
        if (naufragoService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        naufrago.setId(id);
        return ResponseEntity.ok(naufragoService.save(naufrago));
    }

    // DELETE /api/naufragos/{id} → elimina un náufrago por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (naufragoService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        naufragoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
