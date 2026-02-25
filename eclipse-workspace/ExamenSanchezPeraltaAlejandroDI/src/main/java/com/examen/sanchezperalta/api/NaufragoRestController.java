package com.examen.sanchezperalta.api;

import com.examen.sanchezperalta.entity.Naufrago;
import com.examen.sanchezperalta.service.NaufragoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/naufragos")
public class NaufragoRestController {

    @Autowired
    private NaufragoService naufragoService;

    @GetMapping
    public List<Naufrago> listar() {
        return naufragoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Naufrago> getById(@PathVariable Long id) {
        return naufragoService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Naufrago crear(@Valid @RequestBody Naufrago naufrago) {
        return naufragoService.save(naufrago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Naufrago> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody Naufrago naufrago) {
        if (naufragoService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        naufrago.setId(id);
        return ResponseEntity.ok(naufragoService.save(naufrago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (naufragoService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        naufragoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
