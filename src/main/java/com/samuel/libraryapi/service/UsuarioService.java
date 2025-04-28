package com.samuel.libraryapi.service;

import com.samuel.libraryapi.model.Usuario;
import com.samuel.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Usuario user) {
        var senha = user.getSenha();
        user.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(user);
    }
    public Usuario obterPorLogin(String login){
        return usuarioRepository.findByLogin(login);
    }
}
