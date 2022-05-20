package com.example.lp3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Compra {
    private Date dataCompra;
    private Date dataEntrega;
    private double valorTotal;

    @ManyToOne
    private Fornecedor fornecedor;

}
