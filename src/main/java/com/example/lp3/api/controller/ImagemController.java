package com.example.lp3.api.controller;

import com.example.lp3.api.dto.ImagemDTO;
import com.example.lp3.model.entity.Imagem;
import com.example.lp3.service.ImagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/imagens")
@RequiredArgsConstructor
public class ImagemController {
    private final ImagemService service;

    @GetMapping
    public ResponseEntity get() {
        List<Imagem> imagens = service.getImagens();
        return ResponseEntity.ok(imagens.stream().map(ImagemDTO::create).collect(Collectors.toList()));
    }
}
