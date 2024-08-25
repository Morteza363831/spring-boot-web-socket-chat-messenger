package org.examples.springbootwebsocketchatmessenger.controller;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;


@Controller
public class LoginController {

    String keycloakLogoutUrl = "http://localhost:8080/realms/web/protocol/openid-connect/logout";
    String clientId = "web-messenger";

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) throws IOException {

        String redirectUri = request
                .getRequestURL()
                .toString()
                .replace(request.getRequestURI(), "");
        String logoutUrl = keycloakLogoutUrl + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
        request.getSession().invalidate();
        response.sendRedirect(logoutUrl);
    }
}
