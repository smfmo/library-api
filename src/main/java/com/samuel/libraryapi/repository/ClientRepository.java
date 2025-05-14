package com.samuel.libraryapi.repository;

import com.samuel.libraryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);

    boolean existsByClientId(String clientId);
}
