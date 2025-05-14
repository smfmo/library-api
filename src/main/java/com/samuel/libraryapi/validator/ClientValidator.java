package com.samuel.libraryapi.validator;

import com.samuel.libraryapi.exceptions.RegistroDuplicadoException;
import com.samuel.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final ClientRepository repository;
    public void validar(String clientId){
        if (repository.existsByClientId(clientId)) {
            throw new RegistroDuplicadoException("Já existe um cliente cadastrado com esse clientId");
        }
    }
}
