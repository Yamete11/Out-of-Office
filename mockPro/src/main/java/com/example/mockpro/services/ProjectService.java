package com.example.mockpro.services;

import com.example.mockpro.dto.EmployeeDTO;
import com.example.mockpro.dto.ProjectDTO;
import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.Project;
import com.example.mockpro.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectDTO> getAll(){
        return projectRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setProjectType(project.getProjectType());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setProjectManager(project.getProjectManager());
        dto.setComment(project.getComment());
        dto.setStatus(project.getStatus());
        return dto;
    }
}
