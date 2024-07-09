package com.example.mockpro.controllers;

import com.example.mockpro.entities.RequestStatus;
import com.example.mockpro.services.RequestStatusService;
import com.example.mockpro.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/requestStatuses")
public class RequestStatusController {
    private final RequestStatusService requestStatusService;

    @Autowired
    public RequestStatusController(RequestStatusService requestStatusService) {
        this.requestStatusService = requestStatusService;
    }

    @GetMapping
    public ResponseEntity<List<RequestStatus>> getAllStatuses() {
        return new ResponseEntity<>(requestStatusService.getAll(), HttpStatus.OK);
    }
}
