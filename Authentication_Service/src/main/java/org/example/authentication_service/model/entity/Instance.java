package org.example.authentication_service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "instance")
public class Instance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "instance_name", length = 100)
    private String name;

    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)
    private Set<User> users = new LinkedHashSet<>();

}
