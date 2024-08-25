package org.examples.springbootwebsocketchatmessenger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;
}
