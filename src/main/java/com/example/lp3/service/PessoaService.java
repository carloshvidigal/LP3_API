package com.example.lp3.service;

import com.example.lp3.model.entity.Pessoa;
import com.example.lp3.model.repository.PessoaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaService {
    private PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> getPessoas() {
        return repository.findAll();
    }

    public Optional<Pessoa> getPessoaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Transactional
    public void excluir(Pessoa pessoa) {
        Objects.requireNonNull(pessoa.getId());
        repository.delete(pessoa);
    }
}
