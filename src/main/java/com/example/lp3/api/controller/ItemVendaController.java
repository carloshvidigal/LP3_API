package com.example.lp3.api.controller;

import com.example.lp3.api.dto.ItemVendaDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.ItemVenda;
import com.example.lp3.service.ItemVendaService;
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
@RequestMapping("api/v1/itensVenda")
@RequiredArgsConstructor
public class ItemVendaController {
    @Autowired
    private ModelMapper modelMapper;

    private final ItemVendaService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item de Venda encontrados")
    })
    public ResponseEntity get() {
        List<ItemVenda> itensVenda = service.getItensVenda();
        return ResponseEntity.ok(itensVenda.stream().map(ItemVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item de Venda encontrado"),
            @ApiResponse(code = 404, message = "Item de Venda não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Funcionário") Long id) {
        Optional<ItemVenda> itemVenda = service.getItemVendaById(id);
        if (!itemVenda.isPresent()) {
            return new ResponseEntity("Item de Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemVenda.map(ItemVendaDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Item de Venda salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Item de Venda")
    })
    public ResponseEntity post(@RequestBody ItemVendaDTO dto) {
        try {
            ItemVenda itemVendaRequisicao = modelMapper.map(dto, ItemVenda.class);

            ItemVenda itemVenda = service.salvar(itemVendaRequisicao);

            ItemVendaDTO itemVendaResposta = modelMapper.map(itemVenda, ItemVendaDTO.class);

            return new ResponseEntity(itemVendaResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item de Venda salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Item de Venda"),
            @ApiResponse(code = 404, message = "Item de Venda não encontrado")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Funcionário") Long id, @RequestBody ItemVendaDTO dto) {
        if(!service.getItemVendaById(id).isPresent()) {
            return new ResponseEntity("Item de Venda não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            ItemVenda itemVendaRequisicao = modelMapper.map(dto, ItemVenda.class);
            itemVendaRequisicao.setId(id);
            ItemVenda itemVenda = service.salvar(itemVendaRequisicao);

            ItemVendaDTO itemVendaResposta = modelMapper.map(itemVenda, ItemVendaDTO.class);

            return ResponseEntity.ok(itemVendaResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Item de Venda excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Item de Venda"),
            @ApiResponse(code = 404, message = "Item de Venda não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Item de Venda") Long id) {
        Optional<ItemVenda> itemVenda = service.getItemVendaById(id);
        if (!itemVenda.isPresent()) {
            return new ResponseEntity("Item de Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(itemVenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
