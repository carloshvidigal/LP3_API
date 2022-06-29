package com.example.lp3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataCompra;
    private Date dataEntrega;
    private double valorTotal;

    @ManyToOne
    private Fornecedor fornecedor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="compra_id")
    private List<ItemCompra> itensCompra;
}
