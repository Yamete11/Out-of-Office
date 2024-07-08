package com.example.mockpro.services;

import com.example.mockpro.dto.EmployeeDTO;
import com.example.mockpro.dto.LeaveRequestDTO;
import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.LeaveRequest;
import com.example.mockpro.repositories.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public List<LeaveRequestDTO> getAll() {
        return leaveRequestRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private LeaveRequestDTO convertToDTO(LeaveRequest leaveRequest) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setEmployee(leaveRequest.getEmployee());
        dto.setAbsenceReason(leaveRequest.getAbsenceReason());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setComment(leaveRequest.getComment());
        dto.setRequestStatus(leaveRequest.getRequestStatus());
        return dto;
    }
}
