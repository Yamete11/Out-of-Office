package com.example.mockpro.services;

import com.example.mockpro.dto.ApprovalRequestDTO;
import com.example.mockpro.dto.RequestDecisionDTO;
import com.example.mockpro.entities.ApprovalRequest;
import com.example.mockpro.entities.RequestStatus;
import com.example.mockpro.repositories.ApprovalRequestRepository;
import com.example.mockpro.repositories.RequestStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApprovalRequestService {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final RequestStatusRepository requestStatusRepository;

    @Autowired
    public ApprovalRequestService(ApprovalRequestRepository approvalRequestRepository, RequestStatusRepository requestStatusRepository) {
        this.approvalRequestRepository = approvalRequestRepository;
        this.requestStatusRepository = requestStatusRepository;
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

    public void changeStatus(RequestDecisionDTO requestDecisionDTO){
        Optional<ApprovalRequest> appRequest = approvalRequestRepository.findById(requestDecisionDTO.getId());
        if(appRequest.isPresent()){
            ApprovalRequest approvalRequest = appRequest.get();
            Optional<RequestStatus> requestStatus = requestStatusRepository.findById(requestDecisionDTO.getId());
            if (requestStatus.isPresent()) {
                approvalRequest.setRequestStatus(requestStatus.get());
                approvalRequest.setComment(requestDecisionDTO.getComment());
                approvalRequestRepository.save(approvalRequest);
            } else {
                throw new IllegalArgumentException("Invalid status ID");
            }
        } else {
        throw new IllegalArgumentException("Approval request not found");
    }

    }
}
