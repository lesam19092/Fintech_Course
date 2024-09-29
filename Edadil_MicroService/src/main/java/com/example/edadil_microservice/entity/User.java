package com.example.edadil_microservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //TODO сделать в таблице колоку username
    @Column(name = "name" ,unique = true)
    @Size(min = 2, message = "Username must be at least 2 characters long")
    private String username;
    @Column(name = "password" ,unique = true)
    @Size(min = 10, message = "Password must be at least 10 characters long")
    private String password;
    @Column(name = "roles") // Ensure this matches the column name in the database
    private String role;
}