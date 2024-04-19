package com.example.lab3_gtics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oftalmologo")
public class Oftalmologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "nombre",nullable = false,length = 50)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 50)
    private String correo;

    @Column(name = "clinica_id",nullable = false)
    private Integer idClinica;
}
