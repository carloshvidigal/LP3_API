package com.example.lp3.service;

import com.example.lp3.model.entity.ItemVenda;
import com.example.lp3.model.repository.ItemVendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemVendaService {
    private ItemVendaRepository repository;

    public ItemVendaService(ItemVendaRepository repository) {
        this.repository = repository;
    }

    public List<ItemVenda> getVendasCompra() {
        return repository.findAll();
    }

    public Optional<ItemVenda> getItemVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ItemVenda salvar(ItemVenda itemVenda) {
        return repository.save(itemVenda);
    }

    @Transactional
    public void excluir(ItemVenda itemVenda) {
        Objects.requireNonNull(itemVenda.getId());
        repository.delete(itemVenda);
    }
}
