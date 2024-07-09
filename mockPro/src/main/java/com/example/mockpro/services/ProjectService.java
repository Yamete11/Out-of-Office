package com.example.mockpro.services;

import com.example.mockpro.dto.AddProjectDTO;
import com.example.mockpro.dto.EditProjectDTO;
import com.example.mockpro.dto.ProjectDTO;
import com.example.mockpro.dto.UpdateProjectStatusDTO;
import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.Project;
import com.example.mockpro.entities.ProjectType;
import com.example.mockpro.entities.Status;
import com.example.mockpro.repositories.EmployeeRepository;
import com.example.mockpro.repositories.ProjectRepository;
import com.example.mockpro.repositories.ProjectTypeRepository;
import com.example.mockpro.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectTypeRepository projectTypeRepository, EmployeeRepository employeeRepository, StatusRepository statusRepository) {
        this.projectRepository = projectRepository;
        this.projectTypeRepository = projectTypeRepository;
        this.employeeRepository = employeeRepository;
        this.statusRepository = statusRepository;
    }

    public List<ProjectDTO> getAll() {
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

    public void addProject(AddProjectDTO addProjectDTO) {
        Project project = new Project();
        project.setProjectType(projectTypeRepository.findById(addProjectDTO.getProjectTypeId()).orElseThrow(() -> new IllegalArgumentException("Invalid project type ID")));
        project.setStartDate(new Timestamp(addProjectDTO.getStartDate()));
        project.setEndDate(new Timestamp(addProjectDTO.getEndDate()));
        project.setProjectManager(employeeRepository.findById(addProjectDTO.getProjectManagerId()).orElseThrow(() -> new IllegalArgumentException("Invalid project manager ID")));
        project.setComment(addProjectDTO.getComment());
        project.setStatus(statusRepository.findById(addProjectDTO.getStatusId()).orElseThrow(() -> new IllegalArgumentException("Invalid status ID")));
        projectRepository.save(project);
    }

    public void updateProject(EditProjectDTO editProjectDTO) {
        Project project = projectRepository.findById(editProjectDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + editProjectDTO.getId()));
        project.setProjectType(projectTypeRepository.findById(editProjectDTO.getProjectTypeId()).orElseThrow(() -> new IllegalArgumentException("Invalid project type ID")));
        project.setStartDate(new Timestamp(editProjectDTO.getStartDate()));
        project.setEndDate(new Timestamp(editProjectDTO.getEndDate()));
        project.setProjectManager(employeeRepository.findById(editProjectDTO.getProjectManagerId()).orElseThrow(() -> new IllegalArgumentException("Invalid project manager ID")));
        project.setComment(editProjectDTO.getComment());
        project.setStatus(statusRepository.findById(editProjectDTO.getStatusId()).orElseThrow(() -> new IllegalArgumentException("Invalid status ID")));
        projectRepository.save(project);
    }

    public void updateProjectStatus(UpdateProjectStatusDTO updateProjectStatusDTO) {
        Project project = projectRepository.findById(updateProjectStatusDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + updateProjectStatusDTO.getId()));
        project.setStatus(statusRepository.findById(updateProjectStatusDTO.getStatusId()).orElseThrow(() -> new IllegalArgumentException("Invalid status ID")));
        projectRepository.save(project);
    }
}
