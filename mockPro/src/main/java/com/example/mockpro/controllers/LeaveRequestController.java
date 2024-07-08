package com.example.mockpro.controllers;

import com.example.mockpro.dto.LeaveRequestDTO;
import com.example.mockpro.entities.ApprovalRequest;
import com.example.mockpro.entities.LeaveRequest;
import com.example.mockpro.repositories.LeaveRequestRepository;
import com.example.mockpro.services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaverequests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> getAll(){
        return new ResponseEntity<>(leaveRequestService.getAll(), HttpStatus.OK);
    }
}
