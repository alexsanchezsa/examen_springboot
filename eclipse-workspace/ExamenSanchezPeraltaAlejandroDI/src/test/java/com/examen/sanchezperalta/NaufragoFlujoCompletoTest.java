package com.examen.sanchezperalta;

import com.examen.sanchezperalta.entity.Habilidad;
import com.examen.sanchezperalta.entity.Naufrago;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.examen.sanchezperalta.repository.HabilidadRepository;
import com.examen.sanchezperalta.repository.NaufragoRepository;

import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test de flujo completo (ejercicio 5a):
 * Crea un náufrago con dos habilidades, lo guarda comprobando status 200,
 * Los tests se ejecutan en memoria usando H2 
 *  se genera con ObjectMapper.
 */
@SpringBootTest
@AutoConfigureMockMvc
class NaufragoFlujoCompletoTest {

    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper de Jackson para serializar objetos Java a JSON dinámicamente
    @Autowired
    private ObjectMapper objectMapper;

    // Repositorios para limpiar los datos entre tests
    @Autowired
    private NaufragoRepository naufragoRepository;

    @Autowired
    private HabilidadRepository habilidadRepository;

    /**
     * Limpieza tras cada test para garantizar aislamiento entre casos de prueba.
     * Se eliminan primero los náufragos (tabla con FK) y luego las habilidades.
     */
    @AfterEach
    void limpiarDatos() {
        naufragoRepository.deleteAll();
        habilidadRepository.deleteAll();
    }

    @Test
    void flujoCompleto_crearNaufragoConDosHabilidades_apareceEnListado() throws Exception {

        // ============================================================
        // PASO 1: Crear la primera habilidad via API REST (POST /api/habilidades)
        // Se construye el objeto Java y ObjectMapper lo serializa a JSON
        // dinámicamente → sin JSON hardcodeado en el test
        // ============================================================
        Habilidad habilidad1 = new Habilidad();
        habilidad1.setNombre("Pesca");
        habilidad1.setDescripcion("Habilidad para obtener alimentos del mar");
        habilidad1.setDificultad("Media");
        habilidad1.setCategoria("Supervivencia");

        MvcResult resultH1 = mockMvc.perform(post("/api/habilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(habilidad1)))
                .andExpect(status().isOk()) // La API REST devuelve HTTP 200
                .andReturn();

        // Deserializamos la respuesta para obtener la habilidad con su ID generado por H2
        Habilidad h1Guardada = objectMapper.readValue(
                resultH1.getResponse().getContentAsString(), Habilidad.class);

        // ============================================================
        // PASO 2: Crear la segunda habilidad via API REST
        // Mismo proceso: construir objeto → serializar con ObjectMapper → POST
        // ============================================================
        Habilidad habilidad2 = new Habilidad();
        habilidad2.setNombre("Construccion");
        habilidad2.setDescripcion("Habilidad para construir refugios con materiales naturales");
        habilidad2.setDificultad("Alta");
        habilidad2.setCategoria("Ingenieria");

        MvcResult resultH2 = mockMvc.perform(post("/api/habilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(habilidad2)))
                .andExpect(status().isOk())
                .andReturn();

        Habilidad h2Guardada = objectMapper.readValue(
                resultH2.getResponse().getContentAsString(), Habilidad.class);

        // ============================================================
        // PASO 3: Construir el objeto Náufrago con las dos habilidades guardadas
        // Las referencias a habilidades incluyen sus IDs reales (generados en H2)
        // ObjectMapper serializará el objeto completo a JSON sin hardcodear nada
        // ============================================================
        Naufrago naufrago = new Naufrago();
        naufrago.setNombre("Jack Shephard");
        naufrago.setEdad(37);
        naufrago.setSexo("Hombre");
        naufrago.setIsla("Isla Perdida");
        naufrago.setNacionalidad("Americana");
        naufrago.setHabilidades(List.of(h1Guardada, h2Guardada)); // Se asignan las 2 habilidades

        // ============================================================
        // PASO 4: Guardar el náufrago via API REST y COMPROBAR que el status es 200
        // El controlador REST resuelve las habilidades por ID antes de persistir,
        // lo que garantiza que las entidades estén gestionadas por JPA
        // ============================================================
        mockMvc.perform(post("/api/naufragos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(naufrago)))
                .andExpect(status().isOk()); // ← Verificamos HTTP 200 al guardar

        // ============================================================
        // PASO 5: Listar todos los náufragos vía Thymeleaf (GET /naufragos)
        // y verificar que "Jack Shephard" aparece en el HTML renderizado
        // ============================================================
        mockMvc.perform(get("/naufragos"))
                .andExpect(status().isOk()) // El listado devuelve HTTP 200
                .andExpect(content().string(containsString("Jack Shephard"))); // Está en el listado
    }
}
