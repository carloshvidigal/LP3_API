package com.example.lp3.service;

import com.example.lp3.model.entity.MotivoDescarte;
import com.example.lp3.model.repository.MotivoDescarteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MotivoDescarteService {
    private MotivoDescarteRepository repository;

    public MotivoDescarteService(MotivoDescarteRepository repository) {
        this.repository = repository;
    }

    public List<MotivoDescarte> getMotivosDescarte() {
        return repository.findAll();
    }

    public Optional<MotivoDescarte> getMotivoDescarteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public MotivoDescarte salvar(MotivoDescarte motivoDescarte) {
        return repository.save(motivoDescarte);
    }

    @Transactional
    public void excluir(MotivoDescarte motivoDescarte) {
        Objects.requireNonNull(motivoDescarte.getId());
        repository.delete(motivoDescarte);
    }
}
