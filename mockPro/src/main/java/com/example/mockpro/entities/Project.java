package com.example.mockpro.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "projects")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_type_id", nullable = false)
    private ProjectType projectType;

    private Timestamp startDate;

    private Timestamp endDate;

    /*@ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee projectManager;*/

    private String comment;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

}
