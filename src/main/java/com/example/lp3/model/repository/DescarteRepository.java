package com.example.lp3.model.repository;

import com.example.lp3.model.entity.Descarte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DescarteRepository extends JpaRepository<Descarte,Long> {


}
