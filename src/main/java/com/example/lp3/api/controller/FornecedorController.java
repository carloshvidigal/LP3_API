package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FornecedorDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Fornecedor;
import com.example.lp3.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {
    @Autowired
    private ModelMapper modelMapper;

    private final FornecedorService service;

    @GetMapping
    public ResponseEntity get() {
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody FornecedorDTO dto) {
        try {
            Fornecedor fornecedorRequisicao = modelMapper.map(dto, Fornecedor.class);
            Fornecedor fornecedor = service.salvar(fornecedorRequisicao);

            FornecedorDTO fornecedorResposta = modelMapper.map(fornecedor, FornecedorDTO.class);

            return new ResponseEntity(fornecedorResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody FornecedorDTO dto) {
        if (!service.getFornecedorById(id).isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Fornecedor fornecedorRequisicao = modelMapper.map(dto, Fornecedor.class);
            fornecedorRequisicao.setId(id);
            Fornecedor fornecedor = service.salvar(fornecedorRequisicao);

            FornecedorDTO fornecedorResposta = modelMapper.map(fornecedor, FornecedorDTO.class);

            return ResponseEntity.ok(fornecedorResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
