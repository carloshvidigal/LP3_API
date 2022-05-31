package com.example.lp3.api.controller;
import com.example.lp3.api.dto.DescontoDTO;
import com.example.lp3.model.entity.Desconto;
import com.example.lp3.service.DescontoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/descontos")
@RequiredArgsConstructor
public class DescontoController {
    private final DescontoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Desconto> descontos = service.getDescontos();
        return ResponseEntity.ok(descontos.stream().map(DescontoDTO::create).collect(Collectors.toList()));
    }
}
