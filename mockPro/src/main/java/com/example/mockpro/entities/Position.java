package com.example.mockpro.entities;

import jakarta.persistence.*;
import lombok.Data;

/*@Entity
@Table(name = "positions")
@Data*/
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;
}
