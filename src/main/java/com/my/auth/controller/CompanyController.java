package com.my.auth.controller;

import com.my.auth.model.Company;
import com.my.auth.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    CompanyService companyServ;

    @Autowired
    public CompanyController(CompanyService companyServ) {
        this.companyServ = companyServ;
    }

    @PostMapping
    public Company create(@RequestBody Company company) throws Exception {
        return companyServ.create(company);
    }

    @GetMapping("/{id}")
    public Company query(@PathVariable Long id) throws Exception {
        return companyServ.query(id);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable Long id,
                          @RequestBody Company company) throws Exception {
        return companyServ.update(id, company);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) throws Exception {
        companyServ.delete(id);
        return "Success";
    }

    @GetMapping
    public List<Company> all() {
        return companyServ.findAll();
    }
}
