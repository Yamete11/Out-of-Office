package com.example.mockpro.dto;

import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.Position;
import com.example.mockpro.entities.Status;
import com.example.mockpro.entities.Subdivision;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Data
public class EmployeeDTO {
    private Long id;

    private String fullName;

    private String username;

    private String password;

    private int outOfOfficeBalance;

    private Position position;

    private Status status;

    private Subdivision subdivision;

    private Employee peoplePartner;

    private byte[] photo;
}
