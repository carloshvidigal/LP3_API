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

public class Descarte {
    private Integer qtdadeDescarte;
    private Date data;

    @ManyToOne
    private Lote lote;

    @ManyToOne
    private Funcionario funcionario;
}
