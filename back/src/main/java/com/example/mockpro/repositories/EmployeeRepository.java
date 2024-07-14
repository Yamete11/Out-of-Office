package com.example.mockpro.repositories;

import com.example.mockpro.entities.Employee;
import com.example.mockpro.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(long id);

    @Query("SELECT e FROM Employee e JOIN e.position p WHERE p.title = 'HR_MANAGER'")
    List<Employee> findAllByPositionHrManager();

    List<Employee> findByPositionTitle(String hr_manager);
}
