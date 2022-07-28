package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FuncionarioDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Funcionario;
import com.example.lp3.service.FuncionarioService;
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
@RequestMapping("api/v1/funcionarios")
@RequiredArgsConstructor
@Api("API de Funcionario")
public class FuncionarioController {
    @Autowired
    private ModelMapper modelMapper;

    private final FuncionarioService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionários encontrados")
    })
    public ResponseEntity get() {
        List<Funcionario> funcionarios = service.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário encontrado"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Funcionário") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Funcionário salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Funcionário")
    })
    public ResponseEntity post(@RequestBody FuncionarioDTO dto) {
        try {
            Funcionario funcionarioRequisicao = modelMapper.map(dto, Funcionario.class);

            Funcionario funcionario = service.salvar(funcionarioRequisicao);

            FuncionarioDTO funcionarioResposta = modelMapper.map(funcionario, FuncionarioDTO.class);

            return new ResponseEntity(funcionarioResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Funcionário"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Funcionário") Long id, @RequestBody FuncionarioDTO dto) {
        if(!service.getFuncionarioById(id).isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Funcionario funcionarioRequisicao = modelMapper.map(dto, Funcionario.class);
            funcionarioRequisicao.setId(id);
            Funcionario funcionario = service.salvar(funcionarioRequisicao);

            FuncionarioDTO funcionarioResposta = modelMapper.map(funcionario, FuncionarioDTO.class);

            return ResponseEntity.ok(funcionarioResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Funcionário excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Funcionário"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Funcionário") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario .isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funcionario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
