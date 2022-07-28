package com.example.lp3.api.controller;

import com.example.lp3.api.dto.VendaDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Venda;
import com.example.lp3.service.VendaService;
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
@RequestMapping("api/v1/vendas")
@RequiredArgsConstructor
@Api("API de Venda")
public class VendaController {
    @Autowired
    private ModelMapper modelMapper;

    private final VendaService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Vendas encontradas")
    })
    public ResponseEntity get() {
        List<Venda> vendas = service.getVendas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Categoria não Venda")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da Venda") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Venda salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Venda")
    })
    public ResponseEntity post(@RequestBody VendaDTO dto) {
        try {
            Venda vendaRequisicao = modelMapper.map(dto, Venda.class);
            Venda venda = service.salvar(vendaRequisicao);

            VendaDTO vendaResposta = modelMapper.map(venda, VendaDTO.class);

            return new ResponseEntity(vendaResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Venda"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id da Venda") Long id, @RequestBody VendaDTO dto) {
        if(!service.getVendaById(id).isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Venda vendaRequisicao = modelMapper.map(dto, Venda.class);
            vendaRequisicao.setId(id);
            Venda venda = service.salvar(vendaRequisicao);

            VendaDTO vendaResposta = modelMapper.map(venda, VendaDTO.class);

            return ResponseEntity.ok(vendaResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Venda excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a Venda"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id da Venda") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(venda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
