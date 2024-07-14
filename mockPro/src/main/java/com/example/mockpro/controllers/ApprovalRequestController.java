package com.example.mockpro.controllers;

import com.example.mockpro.dto.ApprovalRequestDTO;
import com.example.mockpro.dto.RequestDecisionDTO;
import com.example.mockpro.services.ApprovalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/changeStatus")
    public ResponseEntity<ApprovalRequestDTO> changeStatus(@RequestBody RequestDecisionDTO requestDecisionDTO){
        ApprovalRequestDTO updatedRequest = approvalRequestService.changeStatus(requestDecisionDTO);
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }
}
