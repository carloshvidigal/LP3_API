package com.example.lp3.api.controller;

import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.PermissaoService;
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
@RequestMapping("/api/v1/permissoes")
@RequiredArgsConstructor
public class PermissaoController {
    @Autowired
    private ModelMapper modelMapper;

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

    @PostMapping()
    public ResponseEntity post(@RequestBody PermissaoDTO dto) {
        try {
            Permissao permissaoRequisicao = modelMapper.map(dto, Permissao.class);
            Permissao permissao = service.salvar(permissaoRequisicao);

            PermissaoDTO permissaoResposta = modelMapper.map(permissao, PermissaoDTO.class);
            return new ResponseEntity(permissaoResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody PermissaoDTO dto) {
        if(!service.getPermissaoById(id).isPresent()) {
            return new ResponseEntity("Permissão não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Permissao permissao = modelMapper.map(dto, Permissao.class);
            permissao.setId(id);
            service.salvar(permissao);

            PermissaoDTO permissaoResposta = modelMapper.map(permissao, PermissaoDTO.class);
            return ResponseEntity.ok(permissaoResposta);
        } catch (RegraNegocioException e) {
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
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
