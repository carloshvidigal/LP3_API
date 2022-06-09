package com.example.lp3.api.controller;

import com.example.lp3.api.dto.PessoaDTO;
import com.example.lp3.model.entity.Pessoa;
import com.example.lp3.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/pessoas")
@RequiredArgsConstructor
public class PessoaController {
    private final PessoaService service;

    @GetMapping
    public ResponseEntity get() {
        List<Pessoa> pessoas = service.getPessoas();
        return ResponseEntity.ok(pessoas.stream().map(PessoaDTO::create).collect(Collectors.toList()));
    }
}
