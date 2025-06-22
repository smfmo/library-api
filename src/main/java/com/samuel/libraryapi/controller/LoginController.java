package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.security.CustomAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "pagina de Login")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth){
            System.out.println(customAuth.getUsuario());
        }
        return "Olá, " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String getAuthorizationCode(@RequestParam("code") String code){
        return "Código de autorização: " + code;
    }
}
