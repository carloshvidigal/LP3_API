package com.example.lp3.api.controller;
import com.example.lp3.api.dto.DescontoDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Desconto;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.DescontoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("API de Desconto")
public class DescontoController {
    @Autowired
    private ModelMapper modelMapper;

    private final DescontoService service;

    @GetMapping()
    @ApiResponses({
            @ApiResponse(code = 200, message = "Descontos encontrados")
    })
    public ResponseEntity get() {
        List<Desconto> descontos = service.getDescontos();
        return ResponseEntity.ok(descontos.stream().map(DescontoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Desconto encontrado"),
            @ApiResponse(code = 404, message = "Desconto não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Desconto") Long id) {
        Optional<Desconto> desconto = service.getDescontoById(id);
        if (!desconto.isPresent()) {
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(desconto.map(DescontoDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Desconto salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Desconto")
    })
    public ResponseEntity post(@RequestBody DescontoDTO dto) {
        try {
            Desconto descontoRequisicao = modelMapper.map(dto, Desconto.class);
            Desconto desconto = service.salvar(descontoRequisicao);

            DescontoDTO descontoResposta = modelMapper.map(desconto, DescontoDTO.class);

            return new ResponseEntity(descontoResposta, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Desconto salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Desconto"),
            @ApiResponse(code = 404, message = "Desconto não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Desconto") Long id, @RequestBody DescontoDTO dto) {
        if(!service.getDescontoById(id).isPresent()) {
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Desconto descontoRequisicao = modelMapper.map(dto, Desconto.class);
            descontoRequisicao.setId(id);
            Desconto desconto = service.salvar(descontoRequisicao);

            DescontoDTO descontoResposta = modelMapper.map(desconto, DescontoDTO.class);
            return ResponseEntity.ok(descontoResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desconto excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Desconto"),
            @ApiResponse(code = 404, message = "Desconto não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Desconto") Long id) {
        Optional<Desconto> desconto = service.getDescontoById(id);
        if (!desconto.isPresent()) {
            return new ResponseEntity("Desconto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(desconto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
