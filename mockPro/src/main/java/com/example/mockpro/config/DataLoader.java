package com.example.mockpro.config;

import com.example.mockpro.entities.*;
import com.example.mockpro.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectRepository projectRepository;

    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, StatusRepository statusRepository, ProjectTypeRepository projectTypeRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.projectTypeRepository = projectTypeRepository;
        this.projectRepository = projectRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {

        // Create roles
        Role hrManagerRole = new Role();
        hrManagerRole.setTitle("HR_MANAGER");

        Role projectManagerRole = new Role();
        projectManagerRole.setTitle("PROJECT_MANAGER");

        Role employeeRole = new Role();
        employeeRole.setTitle("EMPLOYEE");

        roleRepository.saveAll(Arrays.asList(hrManagerRole, projectManagerRole, employeeRole));

        // Create users
        User user1 = new User();
        user1.setUsername("hr");
        user1.setPassword(this.passwordEncoder.encode("12345"));
        user1.setRole(hrManagerRole);

        User user2 = new User();
        user2.setUsername("pro");
        user2.setPassword(this.passwordEncoder.encode("12345"));
        user2.setRole(projectManagerRole);

        User user3 = new User();
        user3.setUsername("emp");
        user3.setPassword(this.passwordEncoder.encode("12345"));
        user3.setRole(employeeRole);

        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        // Create statuses
        Status statusActive = new Status();
        statusActive.setTitle("Active");
        Status statusInactive = new Status();
        statusInactive.setTitle("Inactive");

        statusRepository.saveAll(Arrays.asList(statusActive, statusInactive));

        // Create project types
        ProjectType projectType1 = new ProjectType();
        projectType1.setTitle("Health care");
        ProjectType projectType2 = new ProjectType();
        projectType2.setTitle("Marketing");
        ProjectType projectType3 = new ProjectType();
        projectType3.setTitle("IT projects");

        projectTypeRepository.saveAll(Arrays.asList(projectType1, projectType2, projectType3));

        // Create projects without setting projectType and projectManager
        Project project1 = new Project();
        project1.setProjectType(projectType1);
        project1.setStartDate(new Timestamp(System.currentTimeMillis()));
        project1.setComment("Cool comment");
        project1.setStatus(statusActive);

        Project project2 = new Project();
        project2.setProjectType(projectType2);
        project2.setStartDate(new Timestamp(System.currentTimeMillis()));
        project2.setComment("Not cool comment");
        project2.setStatus(statusActive);

        Project project3 = new Project();
        project3.setProjectType(projectType3);
        project3.setStartDate(new Timestamp(System.currentTimeMillis()));
        project3.setComment("Lame comment");
        project3.setStatus(statusInactive);

        projectRepository.saveAll(Arrays.asList(project1, project2, project3));


        /*Status status1 = new Status();
        status1.setTitle("Active");

        Status status2 = new Status();
        status2.setTitle("Inactive");

        statusRepository.saveAll(Arrays.asList(status1, status2));

        ProjectType type1 = new ProjectType();
        type1.setTitle("Development");

        ProjectType type2 = new ProjectType();
        type2.setTitle("Research");

        projectTypeRepository.saveAll(Arrays.asList(type1, type2));

        Project project1 = new Project();
        project1.setProjectType(type1);
        project1.setStartDate(new Date());
        project1.setEndDate(null);
        project1.setComment("Development Project 1");
        project1.setStatus(status1);

        Project project2 = new Project();
        project2.setProjectType(type2);
        project2.setStartDate(new Date());
        project2.setEndDate(null);
        project2.setComment("Research Project 1");
        project2.setStatus(status1);

        projectRepository.saveAll(Arrays.asList(project1, project2));*/

    }
}
