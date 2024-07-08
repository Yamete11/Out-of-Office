package com.example.mockpro.controllers;

import com.example.mockpro.dto.ApprovalRequestDTO;
import com.example.mockpro.dto.LeaveRequestDTO;
import com.example.mockpro.entities.ApprovalRequest;
import com.example.mockpro.entities.LeaveRequest;
import com.example.mockpro.services.ApprovalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/approvalrequest")
public class ApprovalRequestController {

    private final ApprovalRequestService approvalRequestService;

    @Autowired
    public ApprovalRequestController(ApprovalRequestService approvalRequestService) {
        this.approvalRequestService = approvalRequestService;
    }

    @GetMapping
    public ResponseEntity<List<ApprovalRequestDTO>> getAll(){
        return new ResponseEntity<>(approvalRequestService.getAll(), HttpStatus.OK);
    }


}
