package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CargoDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.model.entity.Cargo;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.PermissaoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/permissoes")
@RequiredArgsConstructor
public class PermissaoController {
    private final PermissaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Permissao> permissoes = service.getPermissoes();
        return ResponseEntity.ok(permissoes.stream().map(PermissaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Permissao> permissao = service.getPermissaoById(id);
        if (!permissao.isPresent()) {
            return new ResponseEntity("Permissao não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(permissao.map(PermissaoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(PermissaoDTO dto) {
        try {
            Permissao permissao = converter(dto);
            permissao = service.salvar(permissao);
            return new ResponseEntity(permissao, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, PermissaoDTO dto) {
        if(!service.getPermissaoById(id).isPresent()) {
            return new ResponseEntity("Permissão não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Permissao permissao = converter(dto);
            permissao.setId(id);
            service.salvar(permissao);
            return ResponseEntity.ok(permissao);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Permissao> permissao = service.getPermissaoById(id);
        if (!permissao.isPresent()) {
            return new ResponseEntity("Permissão não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(permissao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Permissao converter(PermissaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Permissao.class);
    }
}
