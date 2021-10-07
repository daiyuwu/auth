package com.my.auth.config;

import com.my.auth.constant.Role;
import com.my.auth.filter.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth", "/auth/parse").permitAll()
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
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .headers().frameOptions().sameOrigin()
            .and()
            .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
