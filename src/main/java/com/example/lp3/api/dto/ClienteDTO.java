package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
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
    private String cpf;

    public static ClienteDTO create(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cliente, ClienteDTO.class);
    }
}
