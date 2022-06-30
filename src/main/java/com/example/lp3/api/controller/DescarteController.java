package com.example.lp3.api.controller;

import com.example.lp3.api.dto.DescarteDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Descarte;
import com.example.lp3.service.DescarteService;
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
@RequestMapping("api/v1/descartes")
@RequiredArgsConstructor
public class DescarteController {
    @Autowired
    private ModelMapper modelMapper;

    private final DescarteService service;

    @GetMapping
    public ResponseEntity get() {
        List<Descarte> descartes = service.getDescartes();
        return ResponseEntity.ok(descartes.stream().map(DescarteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Descarte> descarte = service.getDescarteById(id);
        if (!descarte.isPresent()) {
            return new ResponseEntity("Descarte não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(descarte.map(DescarteDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody DescarteDTO dto) {
        try {
            Descarte descarteRequisicao = modelMapper.map(dto, Descarte.class);
            Descarte descarte = service.salvar(descarteRequisicao);

            DescarteDTO descarteResposta = modelMapper.map(descarte, DescarteDTO.class);
            return new ResponseEntity(descarteResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody DescarteDTO dto) {
        if(!service.getDescarteById(id).isPresent()) {
            return new ResponseEntity("Descarte não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Descarte descarteRequisicao = modelMapper.map(dto, Descarte.class);

            descarteRequisicao.setId(id);
            Descarte descarte =  service.salvar(descarteRequisicao);

            DescarteDTO descarteResposta = modelMapper.map(descarte, DescarteDTO.class);

            return ResponseEntity.ok(descarteResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Descarte> descarte = service.getDescarteById(id);
        if (!descarte.isPresent()) {
            return new ResponseEntity("Descarte não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(descarte.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
