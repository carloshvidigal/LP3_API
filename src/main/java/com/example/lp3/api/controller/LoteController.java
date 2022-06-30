package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CategoriaDTO;
import com.example.lp3.api.dto.LoteDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Categoria;
import com.example.lp3.model.entity.Desconto;
import com.example.lp3.model.entity.Lote;
import com.example.lp3.service.LoteService;
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
@RequestMapping("api/v1/lotes")
@RequiredArgsConstructor
public class LoteController {
    @Autowired
    private ModelMapper modelMapper;

    private final LoteService service;

    @GetMapping
    public ResponseEntity get() {
        List<Lote> lotes = service.getLotes();
        return ResponseEntity.ok(lotes.stream().map(LoteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Lote> lote = service.getLoteById(id);
        if (!lote.isPresent()) {
            return new ResponseEntity("Lote não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lote.map(LoteDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody LoteDTO dto) {
        try {
            Lote loteRequisicao = modelMapper.map(dto, Lote.class);
            Lote lote = service.salvar(loteRequisicao);

            LoteDTO loteResposta = modelMapper.map(lote, LoteDTO.class);

            return new ResponseEntity(loteResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody LoteDTO dto) {
        if(!service.getLoteById(id).isPresent()) {
            return new ResponseEntity("Lote não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Lote loteRequisicao = modelMapper.map(dto, Lote.class);
            loteRequisicao.setId(id);
            Lote lote = service.salvar(loteRequisicao);

            LoteDTO loteResposta = modelMapper.map(lote, LoteDTO.class);

            return ResponseEntity.ok(loteResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Lote> lote = service.getLoteById(id);
        if (!lote.isPresent()) {
            return new ResponseEntity("Lote não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(lote.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
