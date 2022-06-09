package com.example.lp3.api.dto;

import com.example.lp3.model.entity.CarrinhoVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoVendaDTO {
    private Long id;
    private double valorTotal;
    private Long idFuncionario;


    public static CarrinhoVendaDTO create(CarrinhoVenda carrinhoVenda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(carrinhoVenda, CarrinhoVendaDTO.class);
    }
}
