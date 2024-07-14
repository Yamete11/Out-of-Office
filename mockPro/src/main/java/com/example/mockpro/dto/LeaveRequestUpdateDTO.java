package com.example.mockpro.dto;

import lombok.Data;

@Data
public class LeaveRequestUpdateDTO {
    private Long employee;
    private Long absenceReason;
    private String startDate;
    private String endDate;
    private String comment;
}
