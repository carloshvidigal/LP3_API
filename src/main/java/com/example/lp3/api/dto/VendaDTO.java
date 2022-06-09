package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {
    private Long id;
    private Date dataVenda;
    private double valorTotal;
    private String notaFiscal;
    private Long idFuncionario;

    public static VendaDTO create(Venda venda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(venda, VendaDTO.class);
    }
}
