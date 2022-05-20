package com.example.lp3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Permissao {
    private String moduloSistema;
    private boolean todasOperacoes;
    private boolean cadastrar;
    private boolean editar;
    private boolean visualizar;
    private boolean excluir;


}
