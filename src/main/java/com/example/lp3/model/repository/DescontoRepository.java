package com.example.lp3.model.repository;

import com.example.lp3.model.entity.Desconto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DescontoRepository extends JpaRepository<Desconto,Long> {


}
