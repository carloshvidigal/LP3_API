package com.example.lp3.api.controller;

import com.example.lp3.api.dto.ItemCompraDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.ItemCompra;
import com.example.lp3.service.ItemCompraService;
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
@RequestMapping("api/v1/itensCompra")
@RequiredArgsConstructor
public class ItemCompraController {
    @Autowired
    private ModelMapper modelMapper;

    private final ItemCompraService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item de Compra encontrado")
    })
    public ResponseEntity get() {
        List<ItemCompra> itensCompra = service.getItensCompra();
        return ResponseEntity.ok(itensCompra.stream().map(ItemCompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item de Compra encontrado"),
            @ApiResponse(code = 404, message = "Item de Compra não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Item de Compra") Long id) {
        Optional<ItemCompra> itemCompra = service.getItemCompraById(id);
        if (!itemCompra.isPresent()) {
            return new ResponseEntity("Item de Compra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemCompra.map(ItemCompraDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Item de Compra salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Item de Compra")
    })
    public ResponseEntity post(@RequestBody ItemCompraDTO dto) {
        try {
            ItemCompra itemCompraRequisicao = modelMapper.map(dto, ItemCompra.class);
            ItemCompra itemCompra = service.salvar(itemCompraRequisicao);

            ItemCompraDTO itemCompraResposta = modelMapper.map(itemCompra, ItemCompraDTO.class);
            return new ResponseEntity(itemCompraResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item de Compra salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Item de Compra"),
            @ApiResponse(code = 404, message = "Item de Compra não encontrado")
    })

    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Item de Compra") Long id, @RequestBody ItemCompraDTO dto) {
        if(!service.getItemCompraById(id).isPresent()) {
            return new ResponseEntity("Item de Compra não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            ItemCompra itemCompraRequisicao = modelMapper.map(dto, ItemCompra.class);
            itemCompraRequisicao.setId(id);
            ItemCompra itemCompra = service.salvar(itemCompraRequisicao);

            ItemCompraDTO itemCompraResposta = modelMapper.map(itemCompra, ItemCompraDTO.class);
            return ResponseEntity.ok(itemCompraResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Item de Compra excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Item de Compra"),
            @ApiResponse(code = 404, message = "Item de Compra não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Funcionário") Long id) {
        Optional<ItemCompra> itemCompra  = service.getItemCompraById(id);
        if (!itemCompra.isPresent()) {
            return new ResponseEntity("Item de Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(itemCompra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
