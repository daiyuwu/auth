package com.my.auth.config;

import com.my.auth.constant.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeRequests()
            .antMatchers(HttpMethod.GET
                , "/companies/**"
                , "/clients/**").hasAnyRole(
                        Role.SUPER.name()
                        , Role.MANAGER.name()
                        , Role.OPERATOR.name())
            .antMatchers(HttpMethod.POST
                    , "/clients/**"
                    , "/companies/**").hasAnyRole(
                        Role.SUPER.name()
                        , Role.OPERATOR.name())
            .antMatchers(HttpMethod.PUT
                    , "/clients/**"
                    , "/companies/**").hasAnyRole(
                        Role.SUPER.name()
                        , Role.MANAGER.name())
            .antMatchers(HttpMethod.DELETE
                    , "/clients/**"
                    , "/companies/**").hasAnyRole(
                        Role.SUPER.name()
                        , Role.MANAGER.name())
            .anyRequest().permitAll()
            .and()
            .csrf().ignoringAntMatchers("/h2/**")
            .and()
            .headers().frameOptions().sameOrigin()
            .and()
            .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}
