package com.example.lab3_gtics.repository;

import com.example.lab3_gtics.entity.Oftalmologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OftalmologoRepository extends JpaRepository<Oftalmologo,Integer> {
}
