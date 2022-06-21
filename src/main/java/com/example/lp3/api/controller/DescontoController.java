package com.example.lp3.api.controller;
import com.example.lp3.api.dto.DescontoDTO;
import com.example.lp3.model.entity.Desconto;
import com.example.lp3.service.DescontoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/descontos")
@RequiredArgsConstructor
public class DescontoController {
    private final DescontoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Desconto> descontos = service.getDescontos();
        return ResponseEntity.ok(descontos.stream().map(DescontoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Desconto> desconto = service.getDescontoById(id);
        if (!desconto.isPresent()) {
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(desconto.map(DescontoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(DescontoDTO dto) {
        try {
            Desconto desconto = converter(dto);
            desconto = service.salvar(desconto);
            return new ResponseEntity(desconto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Desconto converter(DescontoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Desconto.class);
    }
}
