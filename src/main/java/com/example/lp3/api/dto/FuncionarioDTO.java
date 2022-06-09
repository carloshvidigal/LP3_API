package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String cpf;
    private String senha;
    private Long idCargo;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }
}
