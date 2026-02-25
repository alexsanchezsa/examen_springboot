package com.examen.sanchezperalta.api;

import com.examen.sanchezperalta.dto.GraficoDTO;
import com.examen.sanchezperalta.repository.NaufragoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/graficos")
public class GraficoRestController {

    @Autowired
    private NaufragoRepository naufragoRepository;

    // GET /api/graficos/por-nacionalidad  → datos para el gráfico de barras
    @GetMapping("/por-nacionalidad")
    public List<GraficoDTO> porNacionalidad() {
        return naufragoRepository.countByNacionalidad().stream()
            .map(row -> new GraficoDTO((String) row[0], (Long) row[1]))
            .toList();
    }

    // GET /api/graficos/por-sexo  → datos para el gráfico donut
    @GetMapping("/por-sexo")
    public List<GraficoDTO> porSexo() {
        return naufragoRepository.countBySexo().stream()
            .map(row -> new GraficoDTO((String) row[0], (Long) row[1]))
            .toList();
    }
}
