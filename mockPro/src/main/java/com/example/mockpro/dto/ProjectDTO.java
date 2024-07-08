package com.example.mockpro.dto;

import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.ProjectType;
import com.example.mockpro.entities.Status;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProjectDTO {
    private Long id;

    private ProjectType projectType;

    private Timestamp startDate;

    private Timestamp endDate;

    private Employee projectManager;

    private String comment;

    private Status status;
}
