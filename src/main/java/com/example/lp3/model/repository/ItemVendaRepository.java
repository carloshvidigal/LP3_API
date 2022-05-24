package com.example.lp3.model.repository;

import com.example.lp3.model.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ItemVendaRepository extends JpaRepository<ItemVenda,Long> {


}
