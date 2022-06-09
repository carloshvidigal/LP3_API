package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Descarte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescarteDTO {
    private Long id;
    private Integer qtdadeDescarte;
    private Date data;
    private Long idLote;
    private Long idFuncionario;
    private Long idMotivoDescarte;

    public static DescarteDTO create(Descarte descarte) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(descarte, DescarteDTO.class);
    }
}
