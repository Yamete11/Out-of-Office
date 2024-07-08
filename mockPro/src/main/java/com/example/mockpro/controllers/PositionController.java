package com.example.mockpro.controllers;

import com.example.mockpro.dto.EmployeeDTO;
import com.example.mockpro.entities.Position;
import com.example.mockpro.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {
    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions(){
        return new ResponseEntity<>(positionService.getAll(), HttpStatus.OK);
    }
}
