package com.my.auth.service;

import com.my.auth.constant.Role;
import com.my.auth.dao.UserRepository;
import com.my.auth.exception.ResourceNotFoundException;
import com.my.auth.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repo;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User create(User user) throws ResourceNotFoundException {
        if (user.getId() != null)
            throw new ResourceNotFoundException("user's id is wrong");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return repo.save(user);
    }

    public User query(Long id) throws ResourceNotFoundException {
        Optional<User> userOpt = repo.findById(id);
        User user = userOpt.orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return user;
    }

    public User update(Long id, User user) throws ResourceNotFoundException {
        if (user.getId() == null)
            throw new ResourceNotFoundException("user's id is wrong");
        if (id != user.getId())
            throw new ResourceNotFoundException("sent user's ids are not consistency");
        if (!repo.findById(user.getId()).isPresent())
            throw new ResourceNotFoundException("user's id is not exist");
        return repo.save(user);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<User> userOpt = repo.findById(id);
        if (!userOpt.isPresent())
            throw new ResourceNotFoundException("user's id is not exist");
        repo.deleteById(id);
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public void saveAll(List<User> users) {
        repo.saveAll(users);
    }

    public User findOneByRole(Role role) throws ResourceNotFoundException {
        Optional<User> userOpt = repo.findOneByRole(role.name());
        User user = userOpt.orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return user;
    }

    public User findOneByAccount(String account) throws ResourceNotFoundException {
        Optional<User> userOpt = repo.findOneByAccount(account);
        User user = userOpt.orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return user;
    }
}
