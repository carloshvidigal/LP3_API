package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FuncionarioDTO;
import com.example.lp3.model.entity.Funcionario;
import com.example.lp3.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {
    private final FuncionarioService service;

    @GetMapping
    public ResponseEntity get() {
        List<Funcionario> funcionarios = service.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }
}
