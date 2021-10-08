package com.my.auth.controller;

import com.my.auth.model.database.Client;
import com.my.auth.service.database.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    ClientService clientServ;

    @Autowired
    public ClientController(ClientService clientServ) {
        this.clientServ = clientServ;
    }

    @PostMapping
    public Client create(@RequestBody Client client) throws Exception {
        return clientServ.create(client);
    }

    @GetMapping("/{id}")
    public Client query(@PathVariable Long id) throws Exception {
        return clientServ.query(id);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable Long id,
                          @RequestBody Client client) throws Exception {
        return clientServ.update(id, client);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) throws Exception {
        clientServ.delete(id);
        return "Success";
    }

    @GetMapping
    public List<Client> all() {
        return clientServ.findAll();
    }

    @PostMapping("/batch")
    public List<Client> batch(@RequestBody List<Client> clients) {
        clients.stream().forEach(c -> {
            c.setCreatedAt(new Date());
            c.setUpdatedAt(new Date());
        });
        return clientServ.saveAll(clients);
    }
}
