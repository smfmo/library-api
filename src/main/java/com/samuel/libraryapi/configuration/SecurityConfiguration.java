package com.samuel.libraryapi.configuration;

import com.samuel.libraryapi.security.LoginSocialSuccessHandler;
import com.samuel.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security,
                                                   LoginSocialSuccessHandler successHandler) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable) //desativa CSRF (importantes em APIs Rest Já que não usam cookies)
                .httpBasic(Customizer.withDefaults())// habilita autenticação básica via cabeçalho (Authorization)
                .formLogin(configurer -> {
                    configurer.loginPage("/login");
                        })
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/login/**").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();

                    authorizeRequests.anyRequest().authenticated(); //todas as requisições precisam estar autenticadas

                })
                .oauth2Login(oauth2 ->{
                    oauth2
                            .loginPage("/login")
                            .successHandler(successHandler);
                })
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(Customizer.withDefaults())
                )
                .build();
    }

    //configura o prefixo Role
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); //ignora o prefixo ROLE_
    }

    //configura, no token JWT, o prefixo Scope
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }
}
