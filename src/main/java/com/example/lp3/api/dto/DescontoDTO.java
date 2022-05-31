package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Desconto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescontoDTO {
    private Long id;
    private Date dataInicio;
    private Date dataFinal;

    public static DescontoDTO create(Desconto desconto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(desconto, DescontoDTO.class);
    }
}
