package com.example.mockpro.services;

import com.example.mockpro.dto.LeaveRequestDTO;
import com.example.mockpro.dto.LeaveRequestUpdateDTO;
import com.example.mockpro.entities.*;
import com.example.mockpro.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final AbsenceReasonRepository absenceReasonRepository;
    private final RequestStatusRepository requestStatusRepository;
    private final ApprovalRequestRepository approvalRequestRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository, AbsenceReasonRepository absenceReasonRepository, RequestStatusRepository requestStatusRepository, ApprovalRequestRepository approvalRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.absenceReasonRepository = absenceReasonRepository;
        this.requestStatusRepository = requestStatusRepository;
        this.approvalRequestRepository = approvalRequestRepository;
    }

    public List<LeaveRequestDTO> getAll() {
        return leaveRequestRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LeaveRequestDTO createLeaveRequest(LeaveRequestUpdateDTO createDTO) {
        LeaveRequest leaveRequest = new LeaveRequest();

        Optional<Employee> employee = employeeRepository.findById(createDTO.getEmployee());
        Optional<AbsenceReason> absenceReason = absenceReasonRepository.findById(createDTO.getAbsenceReason());
        RequestStatus newRequestStatus = requestStatusRepository.findByTitle("New");

        if (employee.isPresent() && absenceReason.isPresent() && newRequestStatus != null) {
            leaveRequest.setEmployee(employee.get());
            leaveRequest.setAbsenceReason(absenceReason.get());
            leaveRequest.setComment(createDTO.getComment());
            leaveRequest.setRequestStatus(newRequestStatus);
            try {
                leaveRequest.setStartDate(dateFormat.parse(createDTO.getStartDate()));
                leaveRequest.setEndDate(dateFormat.parse(createDTO.getEndDate()));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format");
            }

            LeaveRequest savedRequest = leaveRequestRepository.save(leaveRequest);
            return convertToDTO(savedRequest);
        } else {
            throw new IllegalArgumentException("Employee, Absence Reason, or Request Status not found");
        }
    }

    public LeaveRequestDTO updateLeaveRequest(Long id, LeaveRequestUpdateDTO updateDTO) {
        Optional<LeaveRequest> optionalLeaveRequest = leaveRequestRepository.findById(id);
        if (optionalLeaveRequest.isPresent()) {
            LeaveRequest leaveRequest = optionalLeaveRequest.get();

            Optional<Employee> employee = employeeRepository.findById(updateDTO.getEmployee());
            Optional<AbsenceReason> absenceReason = absenceReasonRepository.findById(updateDTO.getAbsenceReason());

            if (employee.isPresent() && absenceReason.isPresent()) {
                leaveRequest.setEmployee(employee.get());
                leaveRequest.setAbsenceReason(absenceReason.get());
                leaveRequest.setComment(updateDTO.getComment());
                try {
                    leaveRequest.setStartDate(dateFormat.parse(updateDTO.getStartDate()));
                    leaveRequest.setEndDate(dateFormat.parse(updateDTO.getEndDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format");
                }

                LeaveRequest savedRequest = leaveRequestRepository.save(leaveRequest);
                return convertToDTO(savedRequest);
            } else {
                throw new IllegalArgumentException("Employee or Absence Reason not found");
            }
        } else {
            throw new IllegalArgumentException("Leave request not found");
        }
    }

    public LeaveRequestDTO submitLeaveRequest(Long id) {
        Optional<LeaveRequest> optionalLeaveRequest = leaveRequestRepository.findById(id);
        if (optionalLeaveRequest.isPresent()) {
            LeaveRequest leaveRequest = optionalLeaveRequest.get();
            RequestStatus submittedStatus = requestStatusRepository.findByTitle("Submitted");

            if (submittedStatus != null) {
                leaveRequest.setRequestStatus(submittedStatus);
                LeaveRequest savedRequest = leaveRequestRepository.save(leaveRequest);

                createApprovalRequests(savedRequest);

                return convertToDTO(savedRequest);
            } else {
                throw new IllegalArgumentException("Submitted status not found");
            }
        } else {
            throw new IllegalArgumentException("Leave request not found");
        }
    }

    private void createApprovalRequests(LeaveRequest leaveRequest) {
        List<Employee> hrManagers = employeeRepository.findByPositionTitle("HR_MANAGER");
        RequestStatus newApprovalStatus = requestStatusRepository.findByTitle("New");

        if (newApprovalStatus != null) {
            hrManagers.forEach(hrManager -> {
                ApprovalRequest approvalRequest = new ApprovalRequest();
                approvalRequest.setApprover(hrManager);
                approvalRequest.setLeaveRequest(leaveRequest);
                approvalRequest.setRequestStatus(newApprovalStatus);
                approvalRequest.setComment("New approval request");
                approvalRequestRepository.save(approvalRequest);
            });

        } else {
            throw new IllegalArgumentException("New approval status not found");
        }
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
