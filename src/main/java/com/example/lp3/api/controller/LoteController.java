package com.example.lp3.api.controller;

import com.example.lp3.api.dto.LoteDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Lote;
import com.example.lp3.service.LoteService;
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
@RequestMapping("api/v1/lotes")
@RequiredArgsConstructor
public class LoteController {
    @Autowired
    private ModelMapper modelMapper;

    private final LoteService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lotes encontrados")
    })
    public ResponseEntity get() {
        List<Lote> lotes = service.getLotes();
        return ResponseEntity.ok(lotes.stream().map(LoteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lote encontrado"),
            @ApiResponse(code = 404, message = "Lote não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Lote") Long id) {
        Optional<Lote> lote = service.getLoteById(id);
        if (!lote.isPresent()) {
            return new ResponseEntity("Lote não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lote.map(LoteDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Lote salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Lote")
    })
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lote salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Lote"),
            @ApiResponse(code = 404, message = "Lote não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Funcionário") Long id, @RequestBody LoteDTO dto) {
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
    @ApiResponses({
            @ApiResponse(code = 204, message = "Lote excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Lote"),
            @ApiResponse(code = 404, message = "Lote não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Funcionário") Long id) {
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
