package com.example.mockpro.services;

import com.example.mockpro.dto.ApprovalRequestDTO;
import com.example.mockpro.entities.ApprovalRequest;
import com.example.mockpro.repositories.ApprovalRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprovalRequestService {

    private final ApprovalRequestRepository approvalRequestRepository;

    @Autowired
    public ApprovalRequestService(ApprovalRequestRepository approvalRequestRepository) {
        this.approvalRequestRepository = approvalRequestRepository;
    }

    public List<ApprovalRequestDTO> getAll(){
        return approvalRequestRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ApprovalRequestDTO convertToDTO(ApprovalRequest approvalRequest) {
        ApprovalRequestDTO dto = new ApprovalRequestDTO();
        dto.setId(approvalRequest.getId());
        dto.setApprover(approvalRequest.getApprover());
        dto.setLeaveRequest(approvalRequest.getLeaveRequest());
        dto.setRequestStatus(approvalRequest.getRequestStatus());
        dto.setComment(approvalRequest.getComment());
        dto.setRequestStatus(approvalRequest.getRequestStatus());
        return dto;
    }
}
