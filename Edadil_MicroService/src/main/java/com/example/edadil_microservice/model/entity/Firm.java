package com.example.edadil_microservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "firms")
public class Firm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "firm_name", length = 100)
    private String firmName;

    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL)
    private Set<Product> products = new LinkedHashSet<>();

}
