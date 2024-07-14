package com.example.mockpro.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="requestStatuses")
@Data
public class RequestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

}
