package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CompraDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Compra;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
public class CompraController {
    private final CompraService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Compra> compras = service.getCompras();
        return ResponseEntity.ok(compras.stream().map(CompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(compra.map(CompraDTO::create));
    }
    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, CompraDTO dto) {
        if(!service.getCompraById(id).isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Compra  compra  = converter(dto);
            compra.setId(id);
            service.salvar(compra);
            return ResponseEntity.ok(compra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Compra> compra  = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Compra  converter(CompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Compra.class);
    }
}
