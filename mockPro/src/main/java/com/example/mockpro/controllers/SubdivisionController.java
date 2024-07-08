package com.example.mockpro.controllers;

import com.example.mockpro.entities.Status;
import com.example.mockpro.entities.Subdivision;
import com.example.mockpro.services.StatusService;
import com.example.mockpro.services.SubdivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subdivisions")
public class SubdivisionController {
    private final SubdivisionService subdivisionService;

    @Autowired
    public SubdivisionController(SubdivisionService subdivisionService) {
        this.subdivisionService = subdivisionService;
    }

    @GetMapping
    public ResponseEntity<List<Subdivision>> getAllSubdivisions(){
        return new ResponseEntity<>(subdivisionService.getAll(), HttpStatus.OK);
    }
}
