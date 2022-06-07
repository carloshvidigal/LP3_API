package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CargoDTO;
import com.example.lp3.model.entity.Cargo;
import com.example.lp3.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/cargos")
@RequiredArgsConstructor
public class CargoController {
    private final CargoService service;

    @GetMapping
    public ResponseEntity get() {
        List<Cargo> cargos = service.getCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }
}
