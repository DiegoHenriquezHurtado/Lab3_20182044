package com.example.lab3_gtics.controller;

import com.example.lab3_gtics.entity.Oftalmologo;
import com.example.lab3_gtics.repository.OftalmologoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/oftalmologo")
public class OftalmologoController {
    final OftalmologoRepository oftalmologoRepository;

    public OftalmologoController(OftalmologoRepository oftalmologoRepository){
        this.oftalmologoRepository = oftalmologoRepository;
    }
    @GetMapping(value = "/listar")
    public String listarOftalmologos(Model model){
        List <Oftalmologo> lista = oftalmologoRepository.findAll();
        model.addAttribute("listOftalmologos",lista);
        return "oftalmologo/list";
    }
}
