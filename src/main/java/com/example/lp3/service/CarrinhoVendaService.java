package com.example.lp3.service;

import com.example.lp3.model.entity.CarrinhoVenda;
import com.example.lp3.model.repository.CarrinhoVendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarrinhoVendaService {
    private CarrinhoVendaRepository repository;

    public CarrinhoVendaService(CarrinhoVendaRepository repository) {
        this.repository = repository;
    }

    public List<CarrinhoVenda> getCarrinhosVenda() {
        return repository.findAll();
    }

    public Optional<CarrinhoVenda> getCarrinhoVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public CarrinhoVenda salvar(CarrinhoVenda carrinhoVenda) {
        return repository.save(carrinhoVenda);
    }

    @Transactional
    public void excluir(CarrinhoVenda carrinhoVenda) {
        Objects.requireNonNull(carrinhoVenda.getId());
        repository.delete(carrinhoVenda);
    }
}
