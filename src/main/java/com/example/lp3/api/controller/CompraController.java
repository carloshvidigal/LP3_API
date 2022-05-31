package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CompraDTO;
import com.example.lp3.model.entity.Compra;
import com.example.lp3.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
public class CompraController {
    private final CompraService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Compra> compras = service.getCompras();
        return ResponseEntity.ok(compras.stream().map(CompraDTO::create).collect(Collectors.toList()));
    }
}
