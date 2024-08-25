package org.examples.springbootwebsocketchatmessenger.security;

import org.examples.springbootwebsocketchatmessenger.dto.UserLoginDto;
import org.examples.springbootwebsocketchatmessenger.validation.UserLoginMap;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserLoginMap userLoginMap;

    public CustomAuthenticationProvider(UserLoginMap userLoginMap) {
        this.userLoginMap = userLoginMap;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        UserLoginDto userLoginDto = userLoginMap.map(oidcUser);
        System.out.println(userLoginDto.getUsername());
        System.out.println("sfjasdkf;sadjkl");

        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        null,
                        oidcUser.getAuthorities());

        authenticationToken.setDetails(oidcUser);

        return authenticationToken;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OidcUser.class.isAssignableFrom(authentication);
    }
}
