package com.example.lp3.api.controller;

import com.example.lp3.api.dto.ItemVendaDTO;
import com.example.lp3.model.entity.ItemVenda;
import com.example.lp3.service.ItemVendaService;
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
@RequestMapping("api/v1/itensVenda")
@RequiredArgsConstructor
public class ItemVendaController {
    private final ItemVendaService service;

    @GetMapping
    public ResponseEntity get() {
        List<ItemVenda> itensVenda = service.getItensVenda();
        return ResponseEntity.ok(itensVenda.stream().map(ItemVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ItemVenda> itemVenda = service.getItemVendaById(id);
        if (!itemVenda.isPresent()) {
            return new ResponseEntity("Item de Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemVenda.map(ItemVendaDTO::create));
    }
}
