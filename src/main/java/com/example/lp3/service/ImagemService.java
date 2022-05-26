package com.example.lp3.service;

import com.example.lp3.model.entity.Imagem;
import com.example.lp3.model.repository.ImagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImagemService {
    private ImagemRepository repository;

    public ImagemService(ImagemRepository repository) {
        this.repository = repository;
    }

    public List<Imagem> getImagens() {
        return repository.findAll();
    }

    public Optional<Imagem> getImagemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Imagem salvar(Imagem imagem) {
        return repository.save(imagem);
    }

    @Transactional
    public void excluir(Imagem imagem) {
        Objects.requireNonNull(imagem.getId());
        repository.delete(imagem);
    }
}
