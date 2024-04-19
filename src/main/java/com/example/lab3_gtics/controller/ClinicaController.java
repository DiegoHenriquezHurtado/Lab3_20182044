package com.example.lab3_gtics.controller;

import com.example.lab3_gtics.entity.Clinica;
import com.example.lab3_gtics.repository.ClinicaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ClinicaController {
    final ClinicaRepository clinicaRepository;

    public ClinicaController(ClinicaRepository clinicaRepository){
        this.clinicaRepository = clinicaRepository;
    }

    @GetMapping(value = {"/listar",""})
    public String listarClinicas(Model model){
        List<Clinica> lista = clinicaRepository.findAll();
        model.addAttribute("listClinicas",lista);
        return "clinicas/list";
    }
}
