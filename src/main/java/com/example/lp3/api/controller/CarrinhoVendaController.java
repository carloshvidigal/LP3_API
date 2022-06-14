package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CarrinhoVendaDTO;
import com.example.lp3.model.entity.CarrinhoVenda;
import com.example.lp3.service.CarrinhoVendaService;
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
@RequestMapping("api/v1/carrinhoVendas")
@RequiredArgsConstructor
public class CarrinhoVendaController {
    private final CarrinhoVendaService service;

    @GetMapping
    public ResponseEntity get() {
        List<CarrinhoVenda> carrinhoVendas = service.getCarrinhosVenda();
        return ResponseEntity.ok(carrinhoVendas.stream().map(CarrinhoVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<CarrinhoVenda> carrinhoVenda = service.getCarrinhoVendaById(id);
        if (!carrinhoVenda.isPresent()) {
            return new ResponseEntity("Carrinho de Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(carrinhoVenda.map(CarrinhoVendaDTO::create));
    }
}
