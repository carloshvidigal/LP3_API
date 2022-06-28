package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Fabricante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FabricanteDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private String logradouro;
    private String bairro;
    private String numero;
    private String cep;
    private String complemento;
    private String cidade;
    private String estado;
    private String telefoneFixo;
    private String telefoneCel;
    private String email;

    public static FabricanteDTO create(Fabricante fabricante) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fabricante, FabricanteDTO.class);
    }
}
