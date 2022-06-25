package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CarrinhoVendaDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.CarrinhoVenda;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.CarrinhoVendaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/carrinhoVendas")
@RequiredArgsConstructor
public class CarrinhoVendaController {
    private final CarrinhoVendaService service;

    @GetMapping
    public ResponseEntity get() {
        List<CarrinhoVenda> carrinhoVendas = service.getCarrinhosVenda();
        return ResponseEntity.ok(carrinhoVendas.stream().map(CarrinhoVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<CarrinhoVenda> carrinhoVenda = service.getCarrinhoVendaById(id);
        if (!carrinhoVenda.isPresent()) {
            return new ResponseEntity("Carrinho de Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(carrinhoVenda.map(CarrinhoVendaDTO::create));
    }
    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, CarrinhoVendaDTO dto) {
        if(!service.getCarrinhoVendaById(id).isPresent()) {
            return new ResponseEntity("Carrinho de Vendas não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            CarrinhoVenda carrinhoVenda = converter(dto);
            carrinhoVenda.setId(id);
            service.salvar(carrinhoVenda);
            return ResponseEntity.ok(carrinhoVenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<CarrinhoVenda> carrinhoVenda = service.getCarrinhoVendaById(id);
        if (!carrinhoVenda.isPresent()) {
            return new ResponseEntity("Carrinho de Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(carrinhoVenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public CarrinhoVenda converter(CarrinhoVendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, CarrinhoVenda.class);
    }

}
