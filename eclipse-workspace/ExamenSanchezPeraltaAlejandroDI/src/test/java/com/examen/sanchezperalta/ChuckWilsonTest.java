package com.examen.sanchezperalta;

import com.examen.sanchezperalta.entity.Habilidad;
import com.examen.sanchezperalta.entity.Naufrago;
import com.examen.sanchezperalta.repository.HabilidadRepository;
import com.examen.sanchezperalta.repository.NaufragoRepository;
import com.examen.sanchezperalta.service.HabilidadService;
import com.examen.sanchezperalta.service.NaufragoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test de Chuck y Wilson (ejercicio 5b):
 * Crea dos náufragos ("Chuck" y "Wilson"), cada uno con una habilidad propia,
 * los guarda a través de la capa MVC y comprueba que ambos existen en el sistema.
 *
 * Los tests se ejecutan ÚNICAMENTE EN MEMORIA usando H2.
 * Se usa la capa de servicio para crear habilidades y el controlador MVC
 * (POST /naufragos/guardar) para guardar los náufragos.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ChuckWilsonTest {

    // MockMvc simula las peticiones HTTP al DispatcherServlet del controlador MVC
    @Autowired
    private MockMvc mockMvc;

    // Servicio de habilidades para crear las habilidades de cada náufrago
    @Autowired
    private HabilidadService habilidadService;

    // Servicio de náufragos para verificar la existencia de Chuck y Wilson
    @Autowired
    private NaufragoService naufragoService;

    // Repositorios para limpiar datos entre tests
    @Autowired
    private NaufragoRepository naufragoRepository;

    @Autowired
    private HabilidadRepository habilidadRepository;

    /**
     * Limpieza tras cada test para garantizar aislamiento.
     */
    @AfterEach
    void limpiarDatos() {
        naufragoRepository.deleteAll();
        habilidadRepository.deleteAll();
    }

    @Test
    void crearChuckYWilson_cadaUnoConUnaHabilidad_ambosExistenEnElSistema() throws Exception {

        // ============================================================
        // PASO 1: Crear la habilidad de Chuck a través de la capa de servicio
        // El servicio forma parte de la arquitectura MVC (capa de negocio/servicio)
        // ============================================================
        Habilidad habilidadChuck = new Habilidad();
        habilidadChuck.setNombre("Supervivencia en selva");
        habilidadChuck.setDificultad("Alta");
        habilidadChuck.setCategoria("Supervivencia");

        // Se guarda a través del servicio → la habilidad queda persistida en H2 con su ID
        Habilidad hAbChuck = habilidadService.save(habilidadChuck);

        // ============================================================
        // PASO 2: Guardar a Chuck a través de la capa MVC
        // Se envía un POST al formulario del NaufragoController (/naufragos/guardar)
        // con los parámetros del formulario Thymeleaf (no JSON)
        // El controlador llama al servicio que persiste el náufrago en H2
        // Resultado esperado: redirección 302 hacia /naufragos (comportamiento estándar MVC)
        // ============================================================
        mockMvc.perform(post("/naufragos/guardar")
                        .param("nombre", "Chuck")
                        .param("edad", "40")
                        .param("sexo", "Hombre")
                        .param("isla", "Isla del Pacífico")
                        .param("nacionalidad", "Americana")
                        .param("habilidadesIds", String.valueOf(hAbChuck.getId())))
                .andExpect(status().is3xxRedirection()); // Redirección tras guardar = éxito en MVC

        // ============================================================
        // PASO 3: Crear la habilidad de Wilson a través de la capa de servicio
        // Wilson es un balón de voleibol → su "habilidad" es la comunicación no verbal
        // ============================================================
        Habilidad habilidadWilson = new Habilidad();
        habilidadWilson.setNombre("Comunicacion no verbal");
        habilidadWilson.setDificultad("Baja");
        habilidadWilson.setCategoria("Social");

        Habilidad hAbWilson = habilidadService.save(habilidadWilson);

        // ============================================================
        // PASO 4: Guardar a Wilson a través de la capa MVC (mismo flujo que Chuck)
        // ============================================================
        mockMvc.perform(post("/naufragos/guardar")
                        .param("nombre", "Wilson")
                        .param("edad", "0")
                        .param("sexo", "Otro")
                        .param("isla", "Isla del Pacífico")
                        .param("nacionalidad", "Americana")
                        .param("habilidadesIds", String.valueOf(hAbWilson.getId())))
                .andExpect(status().is3xxRedirection());

        // ============================================================
        // PASO 5: Verificar que CHUCK existe consultando la capa de servicio
        // findAll() recupera todos los náufragos almacenados en H2
        // ============================================================
        List<Naufrago> todos = naufragoService.findAll();
        assertThat(todos)
                .extracting(Naufrago::getNombre)
                .contains("Chuck"); // Chuck debe estar en el listado

        // ============================================================
        // PASO 6: Verificar que WILSON existe en el sistema
        // ============================================================
        assertThat(todos)
                .extracting(Naufrago::getNombre)
                .contains("Wilson"); // Wilson también debe estar en el listado

        // ============================================================
        // PASO 7 (comprobación adicional): Verificar ambos via Thymeleaf (GET /naufragos)
        // La vista HTML renderizada debe contener los nombres de ambos náufragos
        // ============================================================
        mockMvc.perform(get("/naufragos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Chuck")))
                .andExpect(content().string(containsString("Wilson")));
    }
}
