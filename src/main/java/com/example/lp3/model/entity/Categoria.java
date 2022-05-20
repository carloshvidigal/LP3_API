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


public class Categoria {
    private String nome;

    @ManyToOne
    private Desconto desconto;
}
