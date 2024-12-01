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
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "company_name", length = 100)
    private String companyName;

    @OneToMany(mappedBy = "nameOfCompany", cascade = CascadeType.ALL)
    private Set<Shop> shops = new LinkedHashSet<>();

}
