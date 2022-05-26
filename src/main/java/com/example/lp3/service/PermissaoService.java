package com.example.lp3.service;

import com.example.lp3.model.entity.Permissao;
import com.example.lp3.model.repository.PermissaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PermissaoService {
    private PermissaoRepository repository;

    public PermissaoService(PermissaoRepository repository) {
        this.repository = repository;
    }

    public List<Permissao> getPermissoes() {
        return repository.findAll();
    }

    public Optional<Permissao> getPermissaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Permissao salvar(Permissao permissao) {
        return repository.save(permissao);
    }

    @Transactional
    public void excluir(Permissao permissao) {
        Objects.requireNonNull(permissao.getId());
        repository.delete(permissao);
    }
}
