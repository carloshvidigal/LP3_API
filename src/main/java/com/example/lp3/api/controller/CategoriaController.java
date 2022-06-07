package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CategoriaDTO;
import com.example.lp3.model.entity.Categoria;
import com.example.lp3.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService service;

    @GetMapping
    public ResponseEntity get() {
        List<Categoria> categorias = service.getCategorias();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }
}
