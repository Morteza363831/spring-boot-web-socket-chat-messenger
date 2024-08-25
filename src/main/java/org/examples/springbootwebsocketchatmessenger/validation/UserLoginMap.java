package org.examples.springbootwebsocketchatmessenger.validation;

import org.examples.springbootwebsocketchatmessenger.dto.UserLoginDto;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class UserLoginMap {

    public UserLoginDto map(OidcUser oidcUser) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(oidcUser.getPreferredUsername());
        return userLoginDto;
    }

}
