package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Lote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoteDTO {
    private Long id;
    private String codigo;
    private Integer qtdadeProduto;
    private Date dataFabricacao;
    private Date dataValidade;
    private Long idProduto;

    public static LoteDTO create(Lote lote) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(lote, LoteDTO.class);
    }
}
