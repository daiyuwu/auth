package com.my.auth.service;

import com.my.auth.dao.CompanyRepository;
import com.my.auth.model.Company;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository repo;

    @Autowired
    public CompanyService(CompanyRepository repo) {
        this.repo = repo;
    }

    public Company create(Company company) throws Exception {
        if (company.getId() != null)
            new Exception("company's id is wrong");
        return repo.save(company);
    }

    public Company query(Long id) throws Exception {
        Optional<Company> companyOpt = repo.findById(id);
        Company company = companyOpt.orElseThrow(() -> new Exception("company not found"));
        return company;
    }

    public Company update(Long id, Company company) throws Exception {
        if (company.getId() == null)
            new Exception("company's id is wrong");
        if (id != company.getId())
            new Exception("sent company's ids is not consistency");
        if (!repo.findById(company.getId()).isPresent())
            new Exception("company's id is not exist");
        return repo.save(company);
    }

    public void delete(Long id) throws NotFoundException {
        Optional<Company> companyOpt = repo.findById(id);
        if (!companyOpt.isPresent())
            new NotFoundException("company's id is not exist");
        repo.deleteById(id);
    }

    public List<Company> findAll() {
        return repo.findAll();
    }
}
