package com.example.lp3.api.controller;

import com.example.lp3.api.dto.VendaDTO;
import com.example.lp3.model.entity.Venda;
import com.example.lp3.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService service;

    @GetMapping
    public ResponseEntity get() {
        List<Venda> vendas = service.getVendas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }
}
