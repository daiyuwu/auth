package com.my.auth.service;

import com.my.auth.dao.ClientRepository;
import com.my.auth.model.Client;
import javassist.NotFoundException;
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

    public Client create(Client client) throws Exception {
        if (client.getId() != null)
            new Exception("company's id is wrong");
        return repo.save(client);
    }

    public Client query(Long id) throws Exception {
        Optional<Client> clientOpt = repo.findById(id);
        Client client = clientOpt.orElseThrow(() -> new Exception("client not found"));
        return client;
    }

    public Client update(Long id, Client client) throws Exception {
        if (client.getId() == null)
            new Exception("client's id is wrong");
        if (id != client.getId())
            new Exception("sent client's ids is not consistency");
        if (!repo.findById(client.getId()).isPresent())
            new Exception("client's id is not exist");
        return repo.save(client);
    }

    public void delete(Long id) throws NotFoundException {
        Optional<Client> clientOpt = repo.findById(id);
        if (!clientOpt.isPresent())
            new NotFoundException("client's id is not exist");
        repo.deleteById(id);
    }

    public List<Client> findAll() {
        return repo.findAll();
    }
}
