package com.my.auth.service.database;

import com.my.auth.dao.ClientRepository;
import com.my.auth.exception.ResourceNotFoundException;
import com.my.auth.model.database.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository repo;

    @Autowired
    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public Client create(Client client) throws ResourceNotFoundException {
        if (client.getId() != null)
            throw new ResourceNotFoundException("company's id is wrong");
        return repo.save(client);
    }

    public Client query(Long id) throws ResourceNotFoundException {
        Optional<Client> clientOpt = repo.findById(id);
        Client client = clientOpt.orElseThrow(() -> new ResourceNotFoundException("client not found"));
        return client;
    }

    public Client update(Long id, Client client) throws ResourceNotFoundException {
        if (client.getId() == null)
            throw new ResourceNotFoundException("client's id is wrong");
        if (id != client.getId())
            throw new ResourceNotFoundException("sent client's ids are not consistency");
        if (!repo.findById(client.getId()).isPresent())
            throw new ResourceNotFoundException("client's id is not exist");
        return repo.save(client);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Client> clientOpt = repo.findById(id);
        if (!clientOpt.isPresent())
            throw new ResourceNotFoundException("client's id is not exist");
        repo.deleteById(id);
    }

    public List<Client> findAll() {
        return repo.findAll();
    }

    public List<Client> saveAll(List<Client> clients) {
        return repo.saveAll(clients);
    }
}
