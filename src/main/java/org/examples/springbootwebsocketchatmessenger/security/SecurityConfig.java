package org.examples.springbootwebsocketchatmessenger.security;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "org.examples")
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return new GrantedAuthoritiesMapperImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/login", "/css/**", "/js/**", "/images/**","/templates/**").permitAll()
                            .anyRequest().authenticated();
                })
                .authenticationProvider(customAuthenticationProvider)
                .oauth2Login(oauth2 -> {
                    oauth2.userInfoEndpoint(userInfoEndpointConfig -> {
                                userInfoEndpointConfig.userAuthoritiesMapper(this.userAuthoritiesMapper());
                            })
                            /*.successHandler((request, response, authentication) -> {
                                // Custom success logic here
                                OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
                                customAuthenticationProvider.authenticate(authentication);
                                System.out.println("Authentication successful for user: " + authentication.getName());
                                response.sendRedirect("/chat?username=" + oidcUser.getPreferredUsername());
                            });*/
                            .successHandler(customAuthenticationSuccessHandler());
                })
                .csrf(csrf -> {
                    csrf.ignoringRequestMatchers("/h2-console/**");
                });

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                OidcUser oidcUser = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = oidcUser.getPreferredUsername();
                System.out.println(username);
                String redirectUrl = UriComponentsBuilder.fromUriString("/chat")
                        .queryParam("username", username)
                        .build()
                        .toUriString();

                response.sendRedirect(redirectUrl);
            }
        };
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**");
    }

}