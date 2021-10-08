package com.my.auth.service;

import com.my.auth.exception.ResourceNotFoundException;
import com.my.auth.model.database.User;
import com.my.auth.service.database.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomSpringUserService implements UserDetailsService {

    private UserService userServ;

    public CustomSpringUserService(UserService userServ) {
        this.userServ = userServ;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user;
        try {
            user = userServ.findOneByAccount(account);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(user.getAccount())
            .password(user.getPassword())
            .roles(user.getRole())
            .build();

        return userDetails;
    }
}
