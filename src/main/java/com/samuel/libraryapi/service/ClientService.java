package com.samuel.libraryapi.service;

import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.model.Client;
import com.samuel.libraryapi.repository.ClientRepository;
import com.samuel.libraryapi.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientValidator validator;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        validator.validar(client.getClientId());
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);
    }

    public Client obterPorClientId(String clientId){
        return repository.findByClientId(clientId);
    }

    public List<Client> pesquisaByExample(String clientId, String scope) {
        Client client = new Client();
        client.setClientId(clientId);
        client.setScope(scope);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Client> clientExample = Example.of(client, matcher);

        return repository.findAll(clientExample);
    }
}
