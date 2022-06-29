package com.example.lp3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.ManyToOne;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer qtdadeProduto;
    private double valorTotalItem;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Compra compra;
}
