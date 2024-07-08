package com.example.mockpro.dto;

import com.example.mockpro.entities.AbsenceReason;
import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.RequestStatus;
import lombok.Data;

import java.util.Date;

@Data
public class LeaveRequestDTO {
    private Long id;

    private Employee employee;

    private AbsenceReason absenceReason;

    private Date startDate;
    private Date endDate;
    private String comment;

    private RequestStatus requestStatus;
}
