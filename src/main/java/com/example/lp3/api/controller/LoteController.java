package com.example.lp3.api.controller;

import com.example.lp3.api.dto.LoteDTO;
import com.example.lp3.model.entity.Lote;
import com.example.lp3.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/lotes")
@RequiredArgsConstructor
public class LoteController {
    private final LoteService service;

    @GetMapping
    public ResponseEntity get() {
        List<Lote> lotes = service.getLotes();
        return ResponseEntity.ok(lotes.stream().map(LoteDTO::create).collect(Collectors.toList()));
    }
}
