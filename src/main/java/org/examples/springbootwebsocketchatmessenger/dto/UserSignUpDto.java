package org.examples.springbootwebsocketchatmessenger.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.examples.springbootwebsocketchatmessenger.entity.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
@NoArgsConstructor
public class UserSignUpDto implements Serializable {
    Long id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
}