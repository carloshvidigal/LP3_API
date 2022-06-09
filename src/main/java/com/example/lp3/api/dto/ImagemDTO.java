package com.example.lp3.api.dto;

import com.example.lp3.model.entity.Imagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagemDTO {
    private Long id;
    private String caminho;
    private Integer tamanhoEmBytes;
    private String mimeType;

    public static ImagemDTO create(Imagem imagem) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(imagem, ImagemDTO.class);
    }
}
