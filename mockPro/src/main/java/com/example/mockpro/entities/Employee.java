package com.example.mockpro.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String username;

    private String password;

    private int outOfOfficeBalance;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "subdivision_id")
    private Subdivision subdivision;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee peoplePartner;

    @Lob
    private byte[] photo;

}
