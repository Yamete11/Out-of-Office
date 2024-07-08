package com.example.mockpro.controllers;

import com.example.mockpro.dto.ProjectDTO;
import com.example.mockpro.entities.Project;
import com.example.mockpro.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> gatProjects(){
        return new ResponseEntity<>(projectService.getAll(), HttpStatus.OK);
    }
}
