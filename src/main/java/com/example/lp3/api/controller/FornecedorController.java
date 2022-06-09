package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FornecedorDTO;
import com.example.lp3.model.entity.Fornecedor;
import com.example.lp3.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {
    private final FornecedorService service;

    @GetMapping
    public ResponseEntity get() {
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }
}
