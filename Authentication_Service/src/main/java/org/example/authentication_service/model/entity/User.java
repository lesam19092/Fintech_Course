package org.example.authentication_service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 100)
    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "user_role", length = 100)
    @Enumerated(EnumType.STRING)
    private Role role;


    @Column(name = "is_enabled")
    private Boolean enable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instance_id")
    private Instance instance;

    @OneToOne(mappedBy = "user")
    private ConfirmationToken confirmationToken;

    @OneToOne(mappedBy = "user")
    private PasswordResetToken passwordResetToken;

    @OneToMany(mappedBy = "user")
    private Set<Token> tokens = new LinkedHashSet<>();

    public Boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


}