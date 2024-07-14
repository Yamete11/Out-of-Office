package com.example.mockpro.services;

import com.example.mockpro.dto.AddProjectDTO;
import com.example.mockpro.dto.EditProjectDTO;
import com.example.mockpro.dto.ProjectDTO;
import com.example.mockpro.dto.UpdateProjectStatusDTO;
import com.example.mockpro.entities.Project;
import com.example.mockpro.repositories.EmployeeRepository;
import com.example.mockpro.repositories.ProjectRepository;
import com.example.mockpro.repositories.ProjectTypeRepository;
import com.example.mockpro.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
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

    public List<ProjectDTO> getProjects(String searchTerm, String projectType, String projectManager, String status) {
        return projectRepository.findAll().stream()
                .filter(project -> (searchTerm == null || project.getId().toString().contains(searchTerm)) &&
                        (projectType == null || project.getProjectType().getTitle().equalsIgnoreCase(projectType)) &&
                        (projectManager == null || project.getProjectManager().getFullName().equalsIgnoreCase(projectManager)) &&
                        (status == null || project.getStatus().getTitle().equalsIgnoreCase(status)))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
        setProject(project, addProjectDTO.getProjectTypeId(), addProjectDTO.getStartDate(), addProjectDTO.getEndDate(), addProjectDTO.getProjectManagerId(), addProjectDTO.getComment(), addProjectDTO.getStatusId());
    }

    public void updateProject(EditProjectDTO editProjectDTO) {
        Project project = projectRepository.findById(editProjectDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + editProjectDTO.getId()));
        setProject(project, editProjectDTO.getProjectTypeId(), editProjectDTO.getStartDate(), editProjectDTO.getEndDate(), editProjectDTO.getProjectManagerId(), editProjectDTO.getComment(), editProjectDTO.getStatusId());
    }

    private void setProject(Project project, Long projectTypeId, Long startDate, Long endDate, Long projectManagerId, String comment, Long statusId) {
        project.setProjectType(projectTypeRepository.findById(projectTypeId).orElseThrow(() -> new IllegalArgumentException("Invalid project type ID")));
        project.setStartDate(new Timestamp(startDate));
        project.setEndDate(new Timestamp(endDate));
        project.setProjectManager(employeeRepository.findById(projectManagerId).orElseThrow(() -> new IllegalArgumentException("Invalid project manager ID")));
        project.setComment(comment);
        project.setStatus(statusRepository.findById(statusId).orElseThrow(() -> new IllegalArgumentException("Invalid status ID")));
        projectRepository.save(project);
    }

    public void updateProjectStatus(UpdateProjectStatusDTO updateProjectStatusDTO) {
        Project project = projectRepository.findById(updateProjectStatusDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + updateProjectStatusDTO.getId()));
        project.setStatus(statusRepository.findById(updateProjectStatusDTO.getStatusId()).orElseThrow(() -> new IllegalArgumentException("Invalid status ID")));
        projectRepository.save(project);
    }
}
