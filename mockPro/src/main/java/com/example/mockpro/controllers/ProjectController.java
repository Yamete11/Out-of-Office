package com.example.mockpro.controllers;

import com.example.mockpro.dto.AddProjectDTO;
import com.example.mockpro.dto.EditProjectDTO;
import com.example.mockpro.dto.ProjectDTO;
import com.example.mockpro.dto.UpdateProjectStatusDTO;
import com.example.mockpro.entities.Project;
import com.example.mockpro.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?> addProject(@RequestBody AddProjectDTO addProjectDTO) {
        projectService.addProject(addProjectDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProject(@RequestBody EditProjectDTO editProjectDTO) {
        projectService.updateProject(editProjectDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateProjectStatus(@RequestBody UpdateProjectStatusDTO updateProjectStatusDTO) {
        projectService.updateProjectStatus(updateProjectStatusDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
