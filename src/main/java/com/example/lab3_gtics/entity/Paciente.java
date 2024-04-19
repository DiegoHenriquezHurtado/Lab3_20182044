package com.example.lab3_gtics.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "nombre" ,nullable = false,length = 45)
    private String nombre;

    @Column(name = "edad" ,nullable = false)
    private Integer edad;

    @Column(name = "genero" , nullable = false, length = 45)
    private String genero;

    @Column(name = "diagnostico", nullable = false,length = 100)
    private String diagnostico;

    @Column(name = "fecha_cita",nullable = false)
    private String fechaCita;

    @Column(name = "numero_habitacion",nullable = false)
    private Integer nroHabitacion;

    @Column(name = "oftalmologo_id",nullable = false)
    private Integer oftalmologoId;

    @Column(name = "clinica_id",nullable = false)
    private Integer clinicaId;
}
