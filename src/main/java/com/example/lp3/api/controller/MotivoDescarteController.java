package com.example.lp3.api.controller;

import com.example.lp3.api.dto.MotivoDescarteDTO;
import com.example.lp3.model.entity.MotivoDescarte;
import com.example.lp3.service.MotivoDescarteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/motivosDescarte")
@RequiredArgsConstructor
public class MotivoDescarteController {
    private final MotivoDescarteService service;

    @GetMapping
    public ResponseEntity get() {
        List<MotivoDescarte> motivosDescarte = service.getMotivosDescarte();
        return ResponseEntity.ok(motivosDescarte.stream().map(MotivoDescarteDTO::create).collect(Collectors.toList()));
    }
}
