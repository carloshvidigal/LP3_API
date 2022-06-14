package com.example.lp3.api.controller;

import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.PermissaoService;
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
@RequestMapping("/api/v1/permissoes")
@RequiredArgsConstructor
public class PermissaoController {
    private final PermissaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Permissao> permissoes = service.getPermissoes();
        return ResponseEntity.ok(permissoes.stream().map(PermissaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Permissao> permissao = service.getPermissaoById(id);
        if (!permissao.isPresent()) {
            return new ResponseEntity("Permissao não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(permissao.map(PermissaoDTO::create));
    }
}
