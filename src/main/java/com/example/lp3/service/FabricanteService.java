package com.example.lp3.service;

import com.example.lp3.model.entity.Fabricante;
import com.example.lp3.model.repository.FabricanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FabricanteService {
    private FabricanteRepository repository;

    public FabricanteService(FabricanteRepository repository) {
        this.repository = repository;
    }

    public List<Fabricante> getFabricantes() {
        return repository.findAll();
    }

    public Optional<Fabricante> getFabricanteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Fabricante salvar(Fabricante fabricante) {
        return repository.save(fabricante);
    }

    @Transactional
    public void excluir(Fabricante fabricante) {
        Objects.requireNonNull(fabricante.getId());
        repository.delete(fabricante);
    }
}
