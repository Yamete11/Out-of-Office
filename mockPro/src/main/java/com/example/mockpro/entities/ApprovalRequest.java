package com.example.mockpro.entities;

import jakarta.persistence.*;
import lombok.Data;

/*@Entity
@Table(name = "approval_requests")
@Data*/
public class ApprovalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "approver_id", nullable = false)
    private Employee approver;

    @ManyToOne
    @JoinColumn(name = "leave_request_id", nullable = false)
    private LeaveRequest leaveRequest;

    @ManyToOne
    @JoinColumn(name = "request_status_id")
    private RequestStatus requestStatus;

    private String comment;
}
