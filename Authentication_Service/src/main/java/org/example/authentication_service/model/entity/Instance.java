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
@Table(name = "services")
public class Instance {
    //todo поменять название таблицы
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "service_name", length = 100)
    private String name;

    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)
    private Set<User> users = new LinkedHashSet<>();

}