package com.example.lp3.api.controller;

import com.example.lp3.api.dto.UsuarioDTO;
import com.example.lp3.api.dto.TokenDTO;
import com.example.lp3.exception.SenhaInvalidaException;
import com.example.lp3.model.entity.Usuario;
import com.example.lp3.security.JwtService;
import com.example.lp3.service.UsuarioService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário salvo com sucesso")
    })
    public Usuario salvar(@RequestBody Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário logado com sucesso"),
            @ApiResponse(code = 401, message = "Não foi possível logar o usuário")
    })
    public TokenDTO autenticar(@RequestBody UsuarioDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}