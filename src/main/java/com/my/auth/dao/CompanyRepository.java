package com.my.auth.dao;

import com.my.auth.model.database.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    public Optional<Company> findOneByName(String name);
}
