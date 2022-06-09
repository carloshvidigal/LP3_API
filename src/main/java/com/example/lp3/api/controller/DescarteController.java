package com.example.lp3.api.controller;

import com.example.lp3.api.dto.DescarteDTO;
import com.example.lp3.model.entity.Descarte;
import com.example.lp3.service.DescarteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
}
