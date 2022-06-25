package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Permissao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissaoDTO {
    private Long id;
    private String moduloSistema;
    private boolean todasOperacoes;
    private boolean cadastrar;
    private boolean editar;
    private boolean visualizar;
    private boolean excluir;
    private Long idCargo;

    public static PermissaoDTO create(Permissao permissao) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(permissao, PermissaoDTO.class);
    }
}
