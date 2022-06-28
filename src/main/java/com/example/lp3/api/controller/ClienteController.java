package com.example.lp3.api.controller;
;
import com.example.lp3.api.dto.CargoDTO;
import com.example.lp3.api.dto.ClienteDTO;
import com.example.lp3.api.dto.PermissaoDTO;
import com.example.lp3.exception.RegraNegocioException;
import com.example.lp3.model.entity.Cargo;
import com.example.lp3.model.entity.Cliente;
import com.example.lp3.model.entity.Permissao;
import com.example.lp3.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {
    @Autowired
    private ModelMapper modelMapper;

    private final ClienteService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ClienteDTO dto) {
        try {
            Cliente clienteRequisicao = modelMapper.map(dto, Cliente.class);
            Cliente cliente = service.salvar(clienteRequisicao);

            ClienteDTO clienteResposta = modelMapper.map(cliente, ClienteDTO.class);

            return new ResponseEntity(clienteResposta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        if(!service.getClienteById(id).isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Cliente clienteRequisicao = modelMapper.map(dto, Cliente.class);

            clienteRequisicao.setId(id);
            Cliente cliente = service.salvar(clienteRequisicao);

            ClienteDTO clienteResposta = modelMapper.map(cliente, ClienteDTO.class);
            return ResponseEntity.ok(clienteResposta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Cliente>  cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cliente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
