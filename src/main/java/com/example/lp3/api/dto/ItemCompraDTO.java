package com.example.lp3.api.dto;

import com.example.lp3.model.entity.ItemCompra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompraDTO {
    private Long id;
    private Integer qtdadeProduto;
    private double valorTotalItem;
    private Long idProduto;
    private Long idCompra;

    public static ItemCompraDTO create(ItemCompra itemCompra) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(itemCompra, ItemCompraDTO.class);
    }
}
