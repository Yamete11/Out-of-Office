package com.example.mockpro.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "leave_requests")
@Data
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "absence_reason_id")
    private AbsenceReason absenceReason;

    private Date startDate;
    private Date endDate;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "request_status_id")
    private RequestStatus requestStatus;
}
