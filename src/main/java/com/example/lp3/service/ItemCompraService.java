package com.example.lp3.service;

import com.example.lp3.model.entity.ItemCompra;
import com.example.lp3.model.repository.ItemCompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemCompraService {
    private ItemCompraRepository repository;

    public ItemCompraService(ItemCompraRepository repository) {
        this.repository = repository;
    }

    public List<ItemCompra> getItensCompra() {
        return repository.findAll();
    }

    public Optional<ItemCompra> getItemCompraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ItemCompra salvar(ItemCompra itemCompra) {
        return repository.save(itemCompra);
    }

    @Transactional
    public void excluir(ItemCompra itemCompra) {
        Objects.requireNonNull(itemCompra.getId());
        repository.delete(itemCompra);
    }
}
