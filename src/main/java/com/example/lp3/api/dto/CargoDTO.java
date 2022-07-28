package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {
    private Long id;
    private String nome;


    public static CargoDTO create(Cargo cargo) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cargo, CargoDTO.class);
    }
}
