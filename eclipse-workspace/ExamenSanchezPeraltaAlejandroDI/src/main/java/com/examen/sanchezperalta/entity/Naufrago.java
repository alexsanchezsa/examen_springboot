package com.examen.sanchezperalta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "naufragos")
public class Naufrago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    private Integer edad;

    @Size(max = 50)
    @Column(length = 50)
    private String sexo;

    @Size(max = 100)
    @Column(length = 100)
    private String isla;

    @Size(max = 50)
    @Column(length = 50)
    private String nacionalidad;

    private LocalDate fechaRescate;

    // Relación Many-to-Many: un náufrago puede tener varias habilidades
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "naufrago_habilidades",
        joinColumns = @JoinColumn(name = "naufrago_id"),
        inverseJoinColumns = @JoinColumn(name = "habilidad_id")
    )
    private List<Habilidad> habilidades = new ArrayList<>();

    public Naufrago() {}

    public Long getId()             { return id; }
    public void setId(Long id)      { this.id = id; }

    public String getNombre()               { return nombre; }
    public void setNombre(String nombre)    { this.nombre = nombre; }

    public Integer getEdad()            { return edad; }
    public void setEdad(Integer edad)   { this.edad = edad; }

    public String getSexo()             { return sexo; }
    public void setSexo(String sexo)    { this.sexo = sexo; }

    public String getIsla()             { return isla; }
    public void setIsla(String isla)    { this.isla = isla; }

    public String getNacionalidad()                 { return nacionalidad; }
    public void setNacionalidad(String nacionalidad){ this.nacionalidad = nacionalidad; }

    public LocalDate getFechaRescate()                  { return fechaRescate; }
    public void setFechaRescate(LocalDate fechaRescate) { this.fechaRescate = fechaRescate; }

    public List<Habilidad> getHabilidades()                 { return habilidades; }
    public void setHabilidades(List<Habilidad> habilidades) { this.habilidades = habilidades; }
}
