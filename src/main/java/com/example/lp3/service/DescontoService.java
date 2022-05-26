package com.example.lp3.service;

import com.example.lp3.model.entity.Desconto;
import com.example.lp3.model.repository.DescontoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DescontoService {
    private DescontoRepository repository;

    public DescontoService(DescontoRepository repository) {
        this.repository = repository;
    }

    public List<Desconto> getDescontos() {
        return repository.findAll();
    }

    public Optional<Desconto> getDescontoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Desconto salvar(Desconto desconto) {
        return repository.save(desconto);
    }

    @Transactional
    public void excluir(Desconto desconto) {
        Objects.requireNonNull(desconto.getId());
        repository.delete(desconto);
    }
}
