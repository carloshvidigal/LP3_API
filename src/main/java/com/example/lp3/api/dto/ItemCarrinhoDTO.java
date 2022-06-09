package com.example.lp3.api.dto;

import com.example.lp3.model.entity.ItemCarrinho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrinhoDTO {
    private Long id;
    private Integer qtdadeProduto;
    private Integer idProduto;
    private Long idCarrinhoVenda;


    public static ItemCarrinhoDTO create(ItemCarrinho itemCarrinho) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(itemCarrinho, ItemCarrinhoDTO.class);
    }
}
