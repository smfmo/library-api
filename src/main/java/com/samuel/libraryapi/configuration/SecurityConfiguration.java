package com.samuel.libraryapi.configuration;

import com.samuel.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.samuel.libraryapi.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable) //desativa CSRF (importantes em APIs Rest Já que não usam cookies)
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/login/**").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();

                    authorizeRequests.anyRequest().authenticated(); //todas as requisições precisam estar autenticadas

                })
                .httpBasic(Customizer.withDefaults()) // habilita autenticação básica via cabeçalho (Authorization)
                .formLogin(configurer -> {
                    configurer.loginPage("/login");
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService service) {
//        UserDetails user1 = User.builder().
//                username("userTest")
//                .password(encoder.encode("1234"))
//                .roles("USER")
//                .build();
//        UserDetails user2 = User.builder().
//                username("admin")
//                .password(encoder.encode("4321"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
        return new CustomUserDetailsService(service);
    }
}
