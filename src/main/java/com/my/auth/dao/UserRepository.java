package com.my.auth.dao;

import com.my.auth.model.database.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findOneByRole(String role);

    public Optional<User> findOneByAccount(String account);
}
