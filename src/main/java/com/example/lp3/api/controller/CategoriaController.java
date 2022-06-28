package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CategoriaDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Categoria;
import com.example.lp3.model.entity.Desconto;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.CategoriaService;
import com.example.lp3.service.DescontoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    @Autowired
    private ModelMapper modelMapper;

    private final CategoriaService service;
    private final DescontoService descontoService;

    @GetMapping
    public ResponseEntity get() {
        List<Categoria> categorias = service.getCategorias();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

    @PostMapping
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
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody CategoriaDTO dto) {
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
    public ResponseEntity delete(@PathVariable("id") Long id) {
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
