package com.my.auth.controller;

import com.my.auth.model.Client;
import com.my.auth.service.ClientService;
import com.my.auth.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
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
}
