package com.example.lp3.api.controller;

import com.example.lp3.api.dto.CargoDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Cargo;
import com.example.lp3.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/cargos")
@RequiredArgsConstructor
public class CargoController {
    @Autowired
    private ModelMapper modelMapper;

    private final CargoService service;

    @GetMapping
    public ResponseEntity get() {
        List<Cargo> cargos = service.getCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cargo.map(CargoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CargoDTO dto) {
        try {
            Cargo cargoRequisicao = modelMapper.map(dto, Cargo.class);
            Cargo cargo = service.salvar(cargoRequisicao);

            CargoDTO cargoResposta = modelMapper.map(cargo, CargoDTO.class);

            List<PermissaoDTO> permissoesDTO = cargoResposta.getPermissoes().stream().map(permissaoDTO -> {
                permissaoDTO.setIdCargo(cargo.getId());
                return permissaoDTO;
            }).collect(Collectors.toList());

            cargoResposta.setPermissoes(permissoesDTO);

            return new ResponseEntity(cargoResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody CargoDTO dto) {
        if(!service.getCargoById(id).isPresent()) {
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Cargo cargoRequisicao = modelMapper.map(dto, Cargo.class);
            cargoRequisicao.setId(id);
            Cargo cargo = service.salvar(cargoRequisicao);

            CargoDTO cargoResposta = modelMapper.map(cargo, CargoDTO.class);

            List<PermissaoDTO> permissoesDTO = cargoResposta.getPermissoes().stream().map(permissaoDTO -> {
                permissaoDTO.setIdCargo(cargo.getId());
                return permissaoDTO;
            }).collect(Collectors.toList());

            cargoResposta.setPermissoes(permissoesDTO);

            return ResponseEntity.ok(cargoResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo .isPresent()) {
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cargo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
