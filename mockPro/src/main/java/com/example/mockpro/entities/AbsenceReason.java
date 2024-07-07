package com.example.mockpro.entities;

import jakarta.persistence.*;
import lombok.Data;

/*@Entity
@Table(name="absenceReasons")
@Data*/
public class AbsenceReason {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 20)
    private String title;

}
