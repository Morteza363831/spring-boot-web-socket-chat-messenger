package org.examples.springbootwebsocketchatmessenger.dto;

import org.examples.springbootwebsocketchatmessenger.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
@NoArgsConstructor
public class UserLoginDto implements Serializable {
    Long id;
    String username;
}