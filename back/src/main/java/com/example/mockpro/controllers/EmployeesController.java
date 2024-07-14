package com.example.mockpro.controllers;

import com.example.mockpro.dto.AddEmployeeDTO;
import com.example.mockpro.dto.EditEmployeeDTO;
import com.example.mockpro.dto.EmployeeDTO;
import com.example.mockpro.dto.StatusDTO;
import com.example.mockpro.entities.Employee;
import com.example.mockpro.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/hr")
    public ResponseEntity<List<EmployeeDTO>> getAllHR(){
        return new ResponseEntity<>(employeeService.getAllHRManagers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id){
        Optional<Employee> employeeDTO = employeeService.getEmployee(id);
        return employeeDTO
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody StatusDTO status){
        Employee updateEmployee = employeeService.updateStatus(status);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @PutMapping("/employee")
    public ResponseEntity<?> updateStatus(@RequestBody EditEmployeeDTO employeeDTO){
        Employee updateEmployee = employeeService.updateEmployee(employeeDTO);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody AddEmployeeDTO employeeDTO){
        Employee emp = employeeService.addEmployee(employeeDTO);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }
}
