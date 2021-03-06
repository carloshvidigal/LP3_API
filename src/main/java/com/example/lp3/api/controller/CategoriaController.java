package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CategoriaDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Categoria;
import com.example.lp3.model.entity.Desconto;
import com.example.lp3.service.CategoriaService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/categorias")
@RequiredArgsConstructor
@Api("API de Categoria")
public class CategoriaController {
    @Autowired
    private ModelMapper modelMapper;

    private final CategoriaService service;
    private final DescontoService descontoService;

    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categorias encontradas")
    })
    public ResponseEntity get() {
        List<Categoria> categorias = service.getCategorias();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da categoria") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "Categoria salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Categoria")
    })
    public ResponseEntity post(@RequestBody CategoriaDTO dto) {
        try {
            Categoria categoriaRequisicao = modelMapper.map(dto, Categoria.class);
            Desconto desconto = categoriaRequisicao.getDesconto();

            if(desconto != null) {
                descontoService.salvar(categoriaRequisicao.getDesconto());
            }

            Categoria categoria = service.salvar(categoriaRequisicao);

            CategoriaDTO categoriaResposta = modelMapper.map(categoria, CategoriaDTO.class);

            return new ResponseEntity(categoriaResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Categoria"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity put(@PathVariable("id") @ApiParam("Id da categoria") Long id, @RequestBody CategoriaDTO dto) {
        if(!service.getCategoriaById(id).isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Categoria categoriaRequisicao = modelMapper.map(dto, Categoria.class);
            categoriaRequisicao.setId(id);
            Categoria categoria = service.salvar(categoriaRequisicao);

            CategoriaDTO categoriaResposta = modelMapper.map(categoria, CategoriaDTO.class);

            return ResponseEntity.ok(categoriaResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Categoria excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a Categoria"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    public ResponseEntity delete(@PathVariable("id") @ApiParam("Id da categoria") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(categoria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
