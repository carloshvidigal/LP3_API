package com.example.lp3.api.controller;

import com.example.lp3.api.dto.MotivoDescarteDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.MotivoDescarte;
import com.example.lp3.service.MotivoDescarteService;
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
@RequestMapping("api/v1/motivosDescarte")
@RequiredArgsConstructor
public class MotivoDescarteController {
    @Autowired
    private ModelMapper modelMapper;

    private final MotivoDescarteService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Motivos de Descarte encontrados")
    })

    public ResponseEntity get() {
        List<MotivoDescarte> motivosDescarte = service.getMotivosDescarte();
        return ResponseEntity.ok(motivosDescarte.stream().map(MotivoDescarteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Motivo de Descarte encontrado"),
            @ApiResponse(code = 404, message = "Motivo de Descarte não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Motivo de Descarte") Long id) {
        Optional<MotivoDescarte> motivoDescarte = service.getMotivoDescarteById(id);
        if (!motivoDescarte.isPresent()) {
            return new ResponseEntity("Motivo de Descarte não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(motivoDescarte.map(MotivoDescarteDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Motivo de Descarte salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Motivo de Descarte")
    })
    public ResponseEntity post(@RequestBody MotivoDescarteDTO dto) {
        try {
            MotivoDescarte motivoDescarteRequisicao = modelMapper.map(dto, MotivoDescarte.class);
            MotivoDescarte motivoDescarte = service.salvar(motivoDescarteRequisicao);

            MotivoDescarteDTO motivoDescarteResposta = modelMapper.map(motivoDescarte, MotivoDescarteDTO.class);
            return new ResponseEntity(motivoDescarteResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Motivo de Descarte salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Motivo de Descarte"),
            @ApiResponse(code = 404, message = "Motivo de Descarte não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Funcionário") Long id, @RequestBody MotivoDescarteDTO dto) {
        if(!service.getMotivoDescarteById(id).isPresent()) {
            return new ResponseEntity("Motivo de Descarte não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            MotivoDescarte motivoDescarteRequisicao = modelMapper.map(dto, MotivoDescarte.class);
            motivoDescarteRequisicao.setId(id);
            MotivoDescarte motivoDescarte = service.salvar(motivoDescarteRequisicao);

            MotivoDescarteDTO motivoDescarteResposta = modelMapper.map(motivoDescarte, MotivoDescarteDTO.class);
            return ResponseEntity.ok(motivoDescarteResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Motivo de Descarte excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Motivo de Descarte"),
            @ApiResponse(code = 404, message = "Motivo de Descarte não encontrado")
    })
            public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<MotivoDescarte> motivoDescarte  = service.getMotivoDescarteById(id);
        if (!motivoDescarte.isPresent()) {
            return new ResponseEntity("Motivo de Descarte não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(motivoDescarte.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
