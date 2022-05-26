package com.example.lp3.service;

import com.example.lp3.model.entity.Lote;
import com.example.lp3.model.repository.LoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoteService {
    private LoteRepository repository;

    public LoteService(LoteRepository repository) {
        this.repository = repository;
    }

    public List<Lote> getLotes() {
        return repository.findAll();
    }

    public Optional<Lote> getLoteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Lote salvar(Lote lote) {
        return repository.save(lote);
    }

    @Transactional
    public void excluir(Lote lote) {
        Objects.requireNonNull(lote.getId());
        repository.delete(lote);
    }
}
