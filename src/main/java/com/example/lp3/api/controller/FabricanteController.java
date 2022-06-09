package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FabricanteDTO;
import com.example.lp3.model.entity.Fabricante;
import com.example.lp3.service.FabricanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api/v1/fabricantes")
@RequiredArgsConstructor
public class FabricanteController {
    private final FabricanteService service;


    @GetMapping()
    public ResponseEntity get() {
        List<Fabricante> fabricantes = service.getFabricantes();
        return ResponseEntity.ok(fabricantes.stream().map(FabricanteDTO::create).collect(Collectors.toList()));
    }
}