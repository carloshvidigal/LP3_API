package com.example.lp3.service;

import com.example.lp3.model.entity.Descarte;
import com.example.lp3.model.repository.DescarteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DescarteService {
    private DescarteRepository repository;

    public DescarteService(DescarteRepository repository) {
        this.repository = repository;
    }

    public List<Descarte> getDescartes() {
        return repository.findAll();
    }

    public Optional<Descarte> getDescarteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Descarte salvar(Descarte descarte) {
        return repository.save(descarte);
    }

    @Transactional
    public void excluir(Descarte descarte) {
        Objects.requireNonNull(descarte.getId());
        repository.delete(descarte);
    }
}
