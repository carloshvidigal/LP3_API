package com.example.lp3.api.controller;

import com.example.lp3.api.dto.FabricanteDTO;
import com.example.lp3.api.dto.ProdutoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Fabricante;
import com.example.lp3.model.entity.Produto;
import com.example.lp3.service.ProdutoService;
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
@RequestMapping("api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    @Autowired
    private ModelMapper modelMapper;

    private final ProdutoService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontradas")
    })
    public ResponseEntity get() {
        List<Produto> produtos = service.getProdutos();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrada"),
            @ApiResponse(code = 404, message = "Produto não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Produto") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Produto")
    })
    public ResponseEntity post(@RequestBody ProdutoDTO dto) {
        try {
            Produto produtoRequisicao = modelMapper.map(dto, Produto.class);
            Produto produto = service.salvar(produtoRequisicao);

            ProdutoDTO produtoResposta = modelMapper.map(produto, ProdutoDTO.class);
            return new ResponseEntity(produtoResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Produto"),
            @ApiResponse(code = 404, message = "Produto não encontrada")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id do Produto") Long id, @RequestBody ProdutoDTO dto) {
        if(!service.getProdutoById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Produto produtoRequisicao = modelMapper.map(dto, Produto.class);
            produtoRequisicao.setId(id);
            Produto produto = service.salvar(produtoRequisicao);

            ProdutoDTO produtoResposta = modelMapper.map(produto, ProdutoDTO.class);
            return ResponseEntity.ok(produtoResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Produto"),
            @ApiResponse(code = 404, message = "Produto não encontrada")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id do Produto") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
