package com.example.lp3.api.controller;

import com.example.lp3.api.dto.MotivoDescarteDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.MotivoDescarte;
import com.example.lp3.service.MotivoDescarteService;
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
@RequestMapping("api/v1/motivosDescarte")
@RequiredArgsConstructor
public class MotivoDescarteController {
    @Autowired
    private ModelMapper modelMapper;

    private final MotivoDescarteService service;

    @GetMapping
    public ResponseEntity get() {
        List<MotivoDescarte> motivosDescarte = service.getMotivosDescarte();
        return ResponseEntity.ok(motivosDescarte.stream().map(MotivoDescarteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<MotivoDescarte> motivoDescarte = service.getMotivoDescarteById(id);
        if (!motivoDescarte.isPresent()) {
            return new ResponseEntity("Motivo de Descarte não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(motivoDescarte.map(MotivoDescarteDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody MotivoDescarteDTO dto) {
        try {
            MotivoDescarte motivoDescarteRequisicao = modelMapper.map(dto, MotivoDescarte.class);
            MotivoDescarte motivoDescarte = service.salvar(motivoDescarteRequisicao);

            MotivoDescarteDTO motivoDescarteResposta = modelMapper.map(motivoDescarte, MotivoDescarteDTO.class);
            return new ResponseEntity(motivoDescarteResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody MotivoDescarteDTO dto) {
        if(!service.getMotivoDescarteById(id).isPresent()) {
            return new ResponseEntity("Motivo de Descarte não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            MotivoDescarte motivoDescarteRequisicao = modelMapper.map(dto, MotivoDescarte.class);
            motivoDescarteRequisicao.setId(id);
            MotivoDescarte motivoDescarte = service.salvar(motivoDescarteRequisicao);

            MotivoDescarteDTO motivoDescarteResposta = modelMapper.map(motivoDescarte, MotivoDescarteDTO.class);
            return ResponseEntity.ok(motivoDescarteResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<MotivoDescarte> motivoDescarte  = service.getMotivoDescarteById(id);
        if (!motivoDescarte.isPresent()) {
            return new ResponseEntity("Motivo de Descarte não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(motivoDescarte.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
