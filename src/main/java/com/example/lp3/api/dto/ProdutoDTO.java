package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String nome;
    private String principioAtivo;
    private double valorUnitario;
    private Integer qtdadeEstoque;
    private Integer estoqueMax;
    private Integer estoqueMin;
    private Integer pontoRessuprimento;
    private Long idFabricante;
    private Long idCategoria;
    private Long idDesconto;

    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produto, ProdutoDTO.class);
    }
}
