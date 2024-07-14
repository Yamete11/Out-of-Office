package com.example.mockpro.services;

import com.example.mockpro.dto.AddEmployeeDTO;
import com.example.mockpro.dto.EditEmployeeDTO;
import com.example.mockpro.dto.EmployeeDTO;
import com.example.mockpro.dto.StatusDTO;
import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.Status;
import com.example.mockpro.repositories.EmployeeRepository;
import com.example.mockpro.repositories.PositionRepository;
import com.example.mockpro.repositories.StatusRepository;
import com.example.mockpro.repositories.SubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final StatusRepository statusRepository;

    private final SubdivisionRepository subdivisionRepository;

    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, StatusRepository statusRepository, PositionRepository positionRepository, SubdivisionRepository subdivisionRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.statusRepository = statusRepository;
        this.positionRepository = positionRepository;
        this.subdivisionRepository = subdivisionRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getAllHRManagers() {
        return employeeRepository.findAllByPositionHrManager().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<Employee> getEmployee(long id){
        return employeeRepository.findById(id);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        dto.setUsername(employee.getUsername());
        dto.setPassword(employee.getPassword());
        dto.setOutOfOfficeBalance(employee.getOutOfOfficeBalance());
        dto.setPosition(employee.getPosition());
        dto.setStatus(employee.getStatus());
        dto.setSubdivision(employee.getSubdivision());
        dto.setPeoplePartner(employee.getPeoplePartner());
        dto.setPhoto(employee.getPhoto());
        return dto;
    }

    public Employee updateStatus(StatusDTO status){
        Optional<Employee> optionalEmployee = employeeRepository.findById(status.getId());
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found with id: " + status.getId());
        }

        Employee emp = optionalEmployee.get();
        Optional<Status> optionalStatus = statusRepository.findById(status.getStatus());
        if (optionalStatus.isEmpty()) {
            throw new IllegalArgumentException("Status not found with id: " + status.getStatus());
        }

        emp.setStatus(optionalStatus.get());
        return employeeRepository.save(emp);
    }

    public Employee addEmployee(AddEmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFullName(employeeDTO.getFullName());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setOutOfOfficeBalance(employeeDTO.getOutOfOfficeBalance());
        employee.setPosition(positionRepository.findById(employeeDTO.getPosition()).orElse(null));
        employee.setStatus(statusRepository.findById(employeeDTO.getStatus()).orElse(null));
        employee.setSubdivision(subdivisionRepository.findById(employeeDTO.getSubdivision()).orElse(null));
        employee.setPeoplePartner(employeeRepository.findById(employeeDTO.getPeoplePartner()).orElse(null));
        //employee.setPhoto(employeeDTO.getPhoto());
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(EditEmployeeDTO employeeDTO){
        Employee employee = employeeRepository.findEmployeeById(employeeDTO.getId());
        employee.setFullName(employeeDTO.getFullName());
        employee.setUsername(employeeDTO.getUsername());
        employee.setOutOfOfficeBalance(employeeDTO.getOutOfOfficeBalance());
        employee.setPosition(positionRepository.findById(employeeDTO.getPosition()).orElse(null));
        employee.setStatus(statusRepository.findById(employeeDTO.getStatus()).orElse(null));
        employee.setSubdivision(subdivisionRepository.findById(employeeDTO.getSubdivision()).orElse(null));
        employee.setPeoplePartner(employeeRepository.findById(employeeDTO.getPeoplePartner()).orElse(null));
        return employeeRepository.save(employee);
    }
}
