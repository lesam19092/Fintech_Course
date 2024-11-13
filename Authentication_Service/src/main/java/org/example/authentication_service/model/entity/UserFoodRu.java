package org.example.authentication_service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users_foodru")
public class UserFoodRu implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //todo сделать enum ROLE_USER, ROLE_ADMIN
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "password", length = 100)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<TokenFoodru> tokens;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "user_role", length = 100)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return name;
    }
}