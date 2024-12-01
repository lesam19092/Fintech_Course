package com.example.edadil_microservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "user_name", length = 50)
    private String userName;

    @Size(max = 50)
    @Column(name = "role", length = 50)
    private String role;

    @Size(max = 100)
    @Column(name = "password", length = 100)
    private String password;

}
