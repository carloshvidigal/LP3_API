package com.example.lp3.api.dto;

import com.example.lp3.model.entity.MotivoDescarte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotivoDescarteDTO {
    private Long id;
    private String motivo;

    public static MotivoDescarteDTO create(MotivoDescarte motivoDescarte) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(motivoDescarte, MotivoDescarteDTO.class);
    }
}
