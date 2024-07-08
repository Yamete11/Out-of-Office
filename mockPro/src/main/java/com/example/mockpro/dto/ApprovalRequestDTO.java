package com.example.mockpro.dto;

import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.LeaveRequest;
import com.example.mockpro.entities.RequestStatus;
import lombok.Data;

@Data
public class ApprovalRequestDTO {

    private Long id;

    private Employee approver;

    private LeaveRequest leaveRequest;

    private RequestStatus requestStatus;

    private String comment;
}
