package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FabricanteDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Fabricante;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.FabricanteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api/v1/fabricantes")
@RequiredArgsConstructor
public class FabricanteController {
    private final FabricanteService service;


    @GetMapping()
    public ResponseEntity get() {
        List<Fabricante> fabricantes = service.getFabricantes();
        return ResponseEntity.ok(fabricantes.stream().map(FabricanteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fabricante> fabricante = service.getFabricanteById(id);
        if (!fabricante.isPresent()) {
            return new ResponseEntity("Fabricante não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fabricante.map(FabricanteDTO::create));
    }
    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, FabricanteDTO dto) {
        if(!service.getPermissaoById(id).isPresent()) {
            return new ResponseEntity("Fabricante não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Fabricante fabricante = converter(dto);
            fabricante.setId(id);
            service.salvar(fabricante);
            return ResponseEntity.ok(fabricante);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Fabricante> fabricante= service.getFabricanteById(id);
        if (!fabricante.isPresent()) {
            return new ResponseEntity("Fabricante não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fabricante.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Fabricante converter(FabricanteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Fabricante.class);
    }
}
