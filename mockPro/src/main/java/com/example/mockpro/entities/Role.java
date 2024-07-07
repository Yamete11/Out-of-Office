package com.example.mockpro.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String title;

}
