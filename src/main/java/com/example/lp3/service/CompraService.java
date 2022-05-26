package com.example.lp3.service;

import com.example.lp3.model.entity.Compra;
import com.example.lp3.model.repository.CompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompraService {
    private CompraRepository repository;

    public CompraService(CompraRepository repository) {
        this.repository = repository;
    }

    public List<Compra> getCompras() {
        return repository.findAll();
    }

    public Optional<Compra> getCompraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Compra salvar(Compra compra) {return repository.save(compra);
    }

    @Transactional
    public void excluir(Compra compra) {
        Objects.requireNonNull(compra.getId());
        repository.delete(compra);
    }
}
