package com.example.lp3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="cargo_id")
    private List<Permissao> permissoes;
}
