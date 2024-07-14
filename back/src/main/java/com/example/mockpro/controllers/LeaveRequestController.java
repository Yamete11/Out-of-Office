package com.example.mockpro.controllers;

import com.example.mockpro.dto.LeaveRequestDTO;
import com.example.mockpro.dto.LeaveRequestUpdateDTO;
import com.example.mockpro.services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<LeaveRequestDTO>> getAll() {
        return new ResponseEntity<>(leaveRequestService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@RequestBody LeaveRequestUpdateDTO createDTO) {
        LeaveRequestDTO createdRequest = leaveRequestService.createLeaveRequest(createDTO);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequestUpdateDTO updateDTO) {
        LeaveRequestDTO updatedRequest = leaveRequestService.updateLeaveRequest(id, updateDTO);
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<LeaveRequestDTO> submitLeaveRequest(@PathVariable Long id) {
        LeaveRequestDTO submittedRequest = leaveRequestService.submitLeaveRequest(id);
        return new ResponseEntity<>(submittedRequest, HttpStatus.OK);
    }
}
