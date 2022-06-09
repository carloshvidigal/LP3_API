package com.example.lp3.api.dto;

import com.example.lp3.model.entity.ItemVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVendaDTO {
    private Long id;
    private Integer qtdadeProduto;
    private double valorTotalItem;
    private Long idProduto;
    private Long idVenda;

    public static ItemVendaDTO create(ItemVenda itemVenda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(itemVenda, ItemVendaDTO.class);
    }
}
