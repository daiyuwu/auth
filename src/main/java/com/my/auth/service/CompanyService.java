package com.my.auth.service;

import com.my.auth.dao.CompanyRepository;
import com.my.auth.exception.ResourceNotFoundException;
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

    public Company create(Company company) throws ResourceNotFoundException {
        if (company.getId() != null)
            new Exception("company's id is wrong");
        return repo.save(company);
    }

    public Company query(Long id) throws ResourceNotFoundException {
        Optional<Company> companyOpt = repo.findById(id);
        Company company = companyOpt.orElseThrow(() -> new ResourceNotFoundException("company not found"));
        return company;
    }

    public Company update(Long id, Company company) throws ResourceNotFoundException {
        if (company.getId() == null)
            new ResourceNotFoundException("company's id is wrong");
        if (id != company.getId())
            new ResourceNotFoundException("sent company's ids is not consistency");
        if (!repo.findById(company.getId()).isPresent())
            new ResourceNotFoundException("company's id is not exist");
        return repo.save(company);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Company> companyOpt = repo.findById(id);
        if (!companyOpt.isPresent())
            new ResourceNotFoundException("company's id is not exist");
        repo.deleteById(id);
    }

    public List<Company> findAll() {
        return repo.findAll();
    }

    public void saveAll(List<Company> companies) {
        repo.saveAll(companies);
    }

    public Company findOneByName(String name) throws ResourceNotFoundException {
        Optional<Company> companyOpt = repo.findOneByName(name);
        Company company = companyOpt.orElseThrow(() -> new ResourceNotFoundException("company not found"));
        return company;
    }
}
