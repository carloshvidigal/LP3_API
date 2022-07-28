package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FornecedorDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Fornecedor;
import com.example.lp3.service.FornecedorService;
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
@RequestMapping("api/v1/fornecedores")
@RequiredArgsConstructor
@Api("API de Fornecedor")
public class FornecedorController {
    @Autowired
    private ModelMapper modelMapper;

    private final FornecedorService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedores encontrados")
    })
    public ResponseEntity get() {
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor encontrado"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Fornecedor") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }

    @PostMapping()
    @ApiResponses({
            @ApiResponse(code = 201, message = "Fornecedor salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Fornecedor")
    })
    public ResponseEntity post(@RequestBody FornecedorDTO dto) {
        try {
            Fornecedor fornecedorRequisicao = modelMapper.map(dto, Fornecedor.class);
            Fornecedor fornecedor = service.salvar(fornecedorRequisicao);

            FornecedorDTO fornecedorResposta = modelMapper.map(fornecedor, FornecedorDTO.class);

            return new ResponseEntity(fornecedorResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Fornecedor"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Fornecedor") Long id, @RequestBody FornecedorDTO dto) {
        if (!service.getFornecedorById(id).isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Fornecedor fornecedorRequisicao = modelMapper.map(dto, Fornecedor.class);
            fornecedorRequisicao.setId(id);
            Fornecedor fornecedor = service.salvar(fornecedorRequisicao);

            FornecedorDTO fornecedorResposta = modelMapper.map(fornecedor, FornecedorDTO.class);

            return ResponseEntity.ok(fornecedorResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Fornecedor excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Fornecedor"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Fornecedor") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
