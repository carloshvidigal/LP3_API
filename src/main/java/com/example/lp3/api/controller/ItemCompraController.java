package com.example.lp3.api.controller;

import com.example.lp3.api.dto.ItemCompraDTO;
import com.example.lp3.model.entity.ItemCompra;
import com.example.lp3.service.ItemCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/itensCompra")
@RequiredArgsConstructor
public class ItemCompraController {
    private final ItemCompraService service;

    @GetMapping
    public ResponseEntity get() {
        List<ItemCompra> itensCompra = service.getItensCompra();
        return ResponseEntity.ok(itensCompra.stream().map(ItemCompraDTO::create).collect(Collectors.toList()));
    }
}
