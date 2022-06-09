package com.example.lp3.api.controller;

import com.example.lp3.api.dto.ItemCarrinhoDTO;
import com.example.lp3.model.entity.ItemCarrinho;
import com.example.lp3.service.ItemCarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/itensCarrinho")
@RequiredArgsConstructor
public class ItemCarrinhoController {
    private final ItemCarrinhoService service;

    @GetMapping
    public ResponseEntity get() {
        List<ItemCarrinho> itensCarrinho = service.getItensCarrinho();
        return ResponseEntity.ok(itensCarrinho.stream().map(ItemCarrinhoDTO::create).collect(Collectors.toList()));
    }
}
