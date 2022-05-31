package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FabricanteDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.model.entity.Fabricante;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.FabricanteService;
import com.example.lp3.service.PermissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/permissoes")
@RequiredArgsConstructor
public class PermissaoController {
    private final PermissaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Permissao> permissoes = service.getPermissoes();
        return ResponseEntity.ok(permissoes.stream().map(PermissaoDTO::create).collect(Collectors.toList()));
    }
}
