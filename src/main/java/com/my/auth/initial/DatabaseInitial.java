package com.my.auth.initial;

import com.my.auth.constant.Role;
import com.my.auth.exception.ResourceNotFoundException;
import com.my.auth.model.database.Client;
import com.my.auth.model.database.Company;
import com.my.auth.model.database.User;
import com.my.auth.service.database.ClientService;
import com.my.auth.service.database.CompanyService;
import com.my.auth.service.database.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitial implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    private ClientService clientServ;
    private CompanyService companyServ;
    private UserService userServ;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitial(ClientService clientServ
                            , CompanyService companyServ
                            , UserService userServ
                            , PasswordEncoder passwordEncoder) {
        this.clientServ = clientServ;
        this.companyServ = companyServ;
        this.userServ = userServ;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initUser();
        try {
            initCompany();
            initClient();
        } catch (ResourceNotFoundException e) {
            logger.warn("init database data is wrong");
            logger.warn(e.getMessage());
        }
    }

    private void initUser() {
        List<User> users = List.of(
            new User("acc1", passwordEncoder.encode("pw1"), "name1", Role.SUPER)
            , new User("acc2", passwordEncoder.encode("pw2"), "name2", Role.MANAGER)
            , new User("acc3", passwordEncoder.encode("pw3"), "name3", Role.OPERATOR)
        );
        userServ.saveAll(users);
    }

    private void initCompany() throws ResourceNotFoundException {
        User user = userServ.findOneByRole(Role.OPERATOR);
        List<Company> companies = List.of(
            new Company("comp1", "address1", user.getId(), user.getId())
            ,new Company("comp2", "address1", user.getId(), user.getId())
            ,new Company("comp3", "address1", user.getId(), user.getId())
        );
        companyServ.saveAll(companies);
    }

    private void initClient() throws ResourceNotFoundException {
        User user = userServ.findOneByRole(Role.OPERATOR);
        Company company = companyServ.findOneByName("comp1");
        List<Client> clients = List.of(
            new Client(company.getId(), "client1", "email1", "phone1", user.getId(), user.getId())
            , new Client(company.getId(), "client2", "email2", "phone2", user.getId(), user.getId())
            , new Client(company.getId(), "client3", "email3", "phone3", user.getId(), user.getId())
        );
        clientServ.saveAll(clients);
    }
}
