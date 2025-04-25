package com.samuel.libraryapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //desativa CSRF (importantes em APIs Rest Já que não usam cookies)
                .httpBasic(Customizer.withDefaults()) // habilita autenticação básica via cabeçalho (Authorization)
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll();
                })
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.anyRequest().authenticated(); //todas as requisições precisam estar autenticadas
                })
                .build();
    }
}
