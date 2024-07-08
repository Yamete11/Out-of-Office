package com.example.mockpro.config;

import com.example.mockpro.entities.*;
import com.example.mockpro.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectRepository projectRepository;
    private final SubdivisionRepository subdivisionRepository;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;
    private final AbsenceReasonRepository absenceReasonRepository;
    private final RequestStatusRepository requestStatusRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final ApprovalRequestRepository approvalRequestRepository;

    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, StatusRepository statusRepository, ProjectTypeRepository projectTypeRepository, ProjectRepository projectRepository, SubdivisionRepository subdivisionRepository, PositionRepository positionRepository, EmployeeRepository employeeRepository, AbsenceReasonRepository absenceReasonRepository, RequestStatusRepository requestStatusRepository, LeaveRequestRepository leaveRequestRepository, ApprovalRequestRepository approvalRequestRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.projectTypeRepository = projectTypeRepository;
        this.projectRepository = projectRepository;
        this.subdivisionRepository = subdivisionRepository;
        this.positionRepository = positionRepository;
        this.employeeRepository = employeeRepository;
        this.absenceReasonRepository = absenceReasonRepository;
        this.requestStatusRepository = requestStatusRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.approvalRequestRepository = approvalRequestRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {

        Role hrManagerRole = new Role();
        hrManagerRole.setTitle("HR_MANAGER");

        Role projectManagerRole = new Role();
        projectManagerRole.setTitle("PROJECT_MANAGER");

        Role employeeRole = new Role();
        employeeRole.setTitle("EMPLOYEE");

        roleRepository.saveAll(Arrays.asList(hrManagerRole, projectManagerRole, employeeRole));


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


        Status statusActive = new Status();
        statusActive.setTitle("Active");
        Status statusInactive = new Status();
        statusInactive.setTitle("Inactive");

        statusRepository.saveAll(Arrays.asList(statusActive, statusInactive));


        ProjectType projectType1 = new ProjectType();
        projectType1.setTitle("Health care");
        ProjectType projectType2 = new ProjectType();
        projectType2.setTitle("Marketing");
        ProjectType projectType3 = new ProjectType();
        projectType3.setTitle("IT projects");

        projectTypeRepository.saveAll(Arrays.asList(projectType1, projectType2, projectType3));

        Subdivision subdivision1 = new Subdivision();
        subdivision1.setTitle("Sub1");
        Subdivision subdivision2 = new Subdivision();
        subdivision2.setTitle("Sub2");
        Subdivision subdivision3 = new Subdivision();
        subdivision3.setTitle("Sub3");
        subdivisionRepository.saveAll(Arrays.asList(subdivision1, subdivision2, subdivision3));

        Position hrManagerPosition = new Position();
        hrManagerPosition.setTitle("HR_MANAGER");
        Position projectManagerPosition = new Position();
        projectManagerPosition.setTitle("PROJECT_MANAGER");
        Position employeePosition = new Position();
        employeePosition.setTitle("EMPLOYEE");
        positionRepository.saveAll(Arrays.asList(hrManagerPosition, projectManagerPosition, employeePosition));

        Employee emp1 = new Employee();
        emp1.setFullName("John Doe");
        emp1.setUsername("jdoe");
        emp1.setPassword(this.passwordEncoder.encode("password"));
        emp1.setOutOfOfficeBalance(10);
        emp1.setPosition(hrManagerPosition);
        emp1.setStatus(statusActive);
        emp1.setSubdivision(subdivision1);
        emp1.setPeoplePartner(null);

        Employee emp2 = new Employee();
        emp2.setFullName("Jane Smith");
        emp2.setUsername("jsmith");
        emp2.setPassword(this.passwordEncoder.encode("password"));
        emp2.setOutOfOfficeBalance(15);
        emp2.setPosition(projectManagerPosition);
        emp2.setStatus(statusActive);
        emp2.setSubdivision(subdivision2);
        emp2.setPeoplePartner(emp1);

        Employee emp3 = new Employee();
        emp3.setFullName("Michael Johnson");
        emp3.setUsername("mjohnson");
        emp3.setPassword(this.passwordEncoder.encode("password"));
        emp3.setOutOfOfficeBalance(5);
        emp3.setPosition(employeePosition);
        emp3.setStatus(statusInactive);
        emp3.setSubdivision(subdivision3);
        emp3.setPeoplePartner(emp1);

        employeeRepository.saveAll(Arrays.asList(emp1, emp2, emp3));

        Project project1 = new Project();
        project1.setProjectType(projectType1);
        project1.setStartDate(new Timestamp(System.currentTimeMillis()));
        project1.setComment("Cool comment");
        project1.setStatus(statusActive);
        project1.setProjectManager(emp2);

        Project project2 = new Project();
        project2.setProjectType(projectType2);
        project2.setStartDate(new Timestamp(System.currentTimeMillis()));
        project2.setComment("Not cool comment");
        project2.setStatus(statusActive);
        project2.setProjectManager(emp2);


        Project project3 = new Project();
        project3.setProjectType(projectType3);
        project3.setStartDate(new Timestamp(System.currentTimeMillis()));
        project3.setComment("Lame comment");
        project3.setStatus(statusInactive);
        project3.setProjectManager(emp2);


        projectRepository.saveAll(Arrays.asList(project1, project2, project3));

        AbsenceReason vacation = new AbsenceReason();
        vacation.setTitle("Vacation");

        AbsenceReason sickLeave = new AbsenceReason();
        sickLeave.setTitle("Sick Leave");

        AbsenceReason personalLeave = new AbsenceReason();
        personalLeave.setTitle("Personal Leave");

        absenceReasonRepository.saveAll(Arrays.asList(vacation, sickLeave, personalLeave));

        RequestStatus newRequest = new RequestStatus();
        newRequest.setTitle("New");

        RequestStatus approved = new RequestStatus();
        approved.setTitle("Approved");

        RequestStatus rejected = new RequestStatus();
        rejected.setTitle("Rejected");

        requestStatusRepository.saveAll(Arrays.asList(newRequest, approved, rejected));

        LeaveRequest leaveRequest1 = new LeaveRequest();
        leaveRequest1.setEmployee(emp1);
        leaveRequest1.setAbsenceReason(vacation);
        leaveRequest1.setStartDate(new Timestamp(System.currentTimeMillis()));
        leaveRequest1.setEndDate(new Timestamp(System.currentTimeMillis() + 86400000L));
        leaveRequest1.setRequestStatus(newRequest);

        LeaveRequest leaveRequest2 = new LeaveRequest();
        leaveRequest2.setEmployee(emp2);
        leaveRequest2.setAbsenceReason(sickLeave);
        leaveRequest2.setStartDate(new Timestamp(System.currentTimeMillis()));
        leaveRequest2.setEndDate(new Timestamp(System.currentTimeMillis() + 172800000L));
        leaveRequest2.setComment("Not feeling well.");
        leaveRequest2.setRequestStatus(approved);

        LeaveRequest leaveRequest3 = new LeaveRequest();
        leaveRequest3.setEmployee(emp3);
        leaveRequest3.setAbsenceReason(personalLeave);
        leaveRequest3.setStartDate(new Timestamp(System.currentTimeMillis()));
        leaveRequest3.setEndDate(new Timestamp(System.currentTimeMillis() + 259200000L));
        leaveRequest3.setComment("Personal matters.");
        leaveRequest3.setRequestStatus(rejected);

        leaveRequestRepository.saveAll(Arrays.asList(leaveRequest1, leaveRequest2, leaveRequest3));

        ApprovalRequest approvalRequest1 = new ApprovalRequest();
        approvalRequest1.setApprover(emp1);
        approvalRequest1.setLeaveRequest(leaveRequest1);
        approvalRequest1.setRequestStatus(newRequest);
        approvalRequest1.setComment("New request.");

        ApprovalRequest approvalRequest2 = new ApprovalRequest();
        approvalRequest2.setApprover(emp1);
        approvalRequest2.setLeaveRequest(leaveRequest2);
        approvalRequest2.setRequestStatus(approved);
        approvalRequest2.setComment("Approved for sick leave.");

        ApprovalRequest approvalRequest3 = new ApprovalRequest();
        approvalRequest3.setApprover(emp2);
        approvalRequest3.setLeaveRequest(leaveRequest3);
        approvalRequest3.setRequestStatus(rejected);
        approvalRequest3.setComment("Rejected due to insufficient leave balance.");

        approvalRequestRepository.saveAll(Arrays.asList(approvalRequest1, approvalRequest2, approvalRequest3));

    }
}
