package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CategoriaDTO;
import com.example.lp3.api.dto.VendaDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Categoria;
import com.example.lp3.model.entity.Desconto;
import com.example.lp3.model.entity.Venda;
import com.example.lp3.service.VendaService;
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
public class VendaController {
    @Autowired
    private ModelMapper modelMapper;

    private final VendaService service;

    @GetMapping
    public ResponseEntity get() {
        List<Venda> vendas = service.getVendas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }

    @PostMapping
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
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody VendaDTO dto) {
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
    public ResponseEntity delete(@PathVariable("id") Long id) {
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
