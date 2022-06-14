package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CargoDTO;
import com.example.lp3.api.dto.DescarteDTO;
import com.example.lp3.model.entity.Cargo;
import com.example.lp3.model.entity.Descarte;
import com.example.lp3.service.DescarteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/descartes")
@RequiredArgsConstructor
public class DescarteController {
    private final DescarteService service;

    @GetMapping
    public ResponseEntity get() {
        List<Descarte> descartes = service.getDescartes();
        return ResponseEntity.ok(descartes.stream().map(DescarteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Descarte> descarte = service.getDescarteById(id);
        if (!descarte.isPresent()) {
            return new ResponseEntity("Descarte não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(descarte.map(DescarteDTO::create));
    }
}
