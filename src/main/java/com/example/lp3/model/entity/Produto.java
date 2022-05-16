package com.example.lp3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String principioAtivo;
    private double valorUnitario;
    private int qtdadeEstoque;
    private int estoqueMax;
    private int estoqueMin;
    private int pontoRessuprimento;


}
