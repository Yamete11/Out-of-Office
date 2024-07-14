package com.example.mockpro.dto;

import lombok.Data;

@Data
public class AddProjectDTO {
    private Long projectTypeId;
    private Long startDate;
    private Long endDate;
    private Long projectManagerId;
    private String comment;
    private Long statusId;
}