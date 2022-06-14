package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CargoDTO;
import com.example.lp3.api.dto.ItemCompraDTO;
import com.example.lp3.model.entity.Cargo;
import com.example.lp3.model.entity.ItemCompra;
import com.example.lp3.service.ItemCompraService;
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
@RequestMapping("api/v1/itensCompra")
@RequiredArgsConstructor
public class ItemCompraController {
    private final ItemCompraService service;

    @GetMapping
    public ResponseEntity get() {
        List<ItemCompra> itensCompra = service.getItensCompra();
        return ResponseEntity.ok(itensCompra.stream().map(ItemCompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ItemCompra> itemCompra = service.getItemCompraById(id);
        if (!itemCompra.isPresent()) {
            return new ResponseEntity("Item de Compra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemCompra.map(ItemCompraDTO::create));
    }

}
