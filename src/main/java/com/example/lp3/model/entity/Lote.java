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

public class Lote {
    private String codigo;
    private Integer qtdadeProduto;
    private Date dataFabricacao;
    private Date dataValidade;

    @ManyToOne
    private Produto produto;
}
