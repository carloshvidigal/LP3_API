package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CompraDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Compra;
import com.example.lp3.service.CompraService;
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
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
@Api("API de Compra")
public class CompraController {
    @Autowired
    private ModelMapper modelMapper;

    private final CompraService service;

    @GetMapping()
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compras encontradas")
    })
    public ResponseEntity get() {
        List<Compra> compras = service.getCompras();
        return ResponseEntity.ok(compras.stream().map(CompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compra encontrada"),
            @ApiResponse(code = 404, message = "Compra não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da Compra") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(compra.map(CompraDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Compra salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Compra")
    })
    public ResponseEntity post(@RequestBody CompraDTO dto) {
        try {
            Compra compraRequisicao = modelMapper.map(dto, Compra.class);
            Compra compra = service.salvar(compraRequisicao);

            CompraDTO compraResposta = modelMapper.map(compra, CompraDTO.class);
            return new ResponseEntity(compraResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compra salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Compra"),
            @ApiResponse(code = 404, message = "Compra não encontrada")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id da Compra") Long id, @RequestBody CompraDTO dto) {
        if(!service.getCompraById(id).isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Compra compraRequisicao = modelMapper.map(dto, Compra.class);
            compraRequisicao.setId(id);
            Compra compra = service.salvar(compraRequisicao);

            CompraDTO compraResposta = modelMapper.map(compra, CompraDTO.class);
            return ResponseEntity.ok(compraResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Compra excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a Compra"),
            @ApiResponse(code = 404, message = "Compra não encontrada")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id da Compra") Long id) {
        Optional<Compra> compra  = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
