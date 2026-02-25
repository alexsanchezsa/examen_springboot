package com.examen.sanchezperalta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "habilidades")
public class Habilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Size(max = 20)
    @Column(length = 20)
    private String dificultad;

    private Integer experiencia;

    @Column(length = 50)
    private String categoria;

    public Habilidad() {
    }

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getDificultad() {
		return dificultad;
	}

	public Integer getExperiencia() {
		return experiencia;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}


	public void setExperiencia(Integer experiencia) {
		this.experiencia = experiencia;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

   
}
