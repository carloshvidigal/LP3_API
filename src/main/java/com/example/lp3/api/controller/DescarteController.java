package com.example.lp3.api.controller;

import com.example.lp3.api.dto.DescarteDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Descarte;
import com.example.lp3.service.DescarteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api("API de Descarte")
public class DescarteController {
    @Autowired
    private ModelMapper modelMapper;

    private final DescarteService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Descartes encontrados")
    })
    public ResponseEntity get() {
        List<Descarte> descartes = service.getDescartes();
        return ResponseEntity.ok(descartes.stream().map(DescarteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Descarte encontrado"),
            @ApiResponse(code = 404, message = "Descarte não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Descarte") Long id) {
        Optional<Descarte> descarte = service.getDescarteById(id);
        if (!descarte.isPresent()) {
            return new ResponseEntity("Descarte não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(descarte.map(DescarteDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Descarte salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Descarte")
    })
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "Descarte salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Descarte"),
            @ApiResponse(code = 404, message = "Descarte não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Descarte") Long id, @RequestBody DescarteDTO dto) {
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
    @ApiResponses({
            @ApiResponse(code = 204, message = "Descarte excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Descarte"),
            @ApiResponse(code = 404, message = "Descarte não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Descarte") Long id) {
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
