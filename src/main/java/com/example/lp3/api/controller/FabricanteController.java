package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FabricanteDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Fabricante;
import com.example.lp3.service.FabricanteService;
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
@RequestMapping ("/api/v1/fabricantes")
@RequiredArgsConstructor
@Api("API de Fabricante")
public class FabricanteController {
    @Autowired
    private ModelMapper modelMapper;

    private final FabricanteService service;

    @GetMapping()
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fabricantes encontrados")
    })
    public ResponseEntity get() {
        List<Fabricante> fabricantes = service.getFabricantes();
        return ResponseEntity.ok(fabricantes.stream().map(FabricanteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fabricante encontrado"),
            @ApiResponse(code = 404, message = "Fabricante não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Fabricante") Long id) {
        Optional<Fabricante> fabricante = service.getFabricanteById(id);
        if (!fabricante.isPresent()) {
            return new ResponseEntity("Fabricante não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fabricante.map(FabricanteDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Fabricante salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Fabricante")
    })
    public ResponseEntity post(@RequestBody FabricanteDTO dto) {
        try {
            Fabricante fabricanteRequisicao = modelMapper.map(dto, Fabricante.class);
            Fabricante fabricante = service.salvar(fabricanteRequisicao);

            FabricanteDTO fabricanteResposta = modelMapper.map(fabricante, FabricanteDTO.class);
            return new ResponseEntity(fabricanteResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fabricante salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Fabricante"),
            @ApiResponse(code = 404, message = "Fabricante não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Fabricante") Long id, @RequestBody FabricanteDTO dto) {
        if(!service.getFabricanteById(id).isPresent()) {
            return new ResponseEntity("Fabricante não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Fabricante fabricanteRequisicao = modelMapper.map(dto, Fabricante.class);
            fabricanteRequisicao.setId(id);
            Fabricante fabricante = service.salvar(fabricanteRequisicao);

            FabricanteDTO fabricanteResposta = modelMapper.map(fabricante, FabricanteDTO.class);
            return ResponseEntity.ok(fabricanteResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Fabricante excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Fabricante"),
            @ApiResponse(code = 404, message = "Fabricante não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Fabricante") Long id) {
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
}
