package com.example.mockpro.controllers;

import com.example.mockpro.entities.Position;
import com.example.mockpro.entities.Status;
import com.example.mockpro.services.PositionService;
import com.example.mockpro.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {
    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses(){
        return new ResponseEntity<>(statusService.getAll(), HttpStatus.OK);
    }
}
