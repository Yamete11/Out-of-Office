Out of Office
 
This application is designed to manage employees, projects, leave requests, and approval requests within a company. It includes functionalities for different roles such as HR Manager, Project Manager, and Employees to access and manage the respective information.
 
Technologies Used
  Frontend:
    React: A JavaScript library for building user interfaces.
    Axios: A promise-based HTTP client for the browser and Node.js, used for making API requests.
    CSS: Used for styling the application, with a common stylesheet (CommonPage.css) for consistent design.
  Backend:
    Spring Boot: A Java-based framework used for building the backend of the application.
    Spring Security: Used for securing the application and handling authentication and authorization.
    JWT (JSON Web Tokens): Used for securely transmitting information between parties as a JSON object.
    JPA (Java Persistence API): Used for database operations.
  PostgreSQL: A relational database management system used to store application data.

Prerequisites
  Node.js: Required to run the frontend application.
  Java JDK: Required to run the backend application.
  PostgreSQL: Required to host the application database.

To run the app: 

1. Clone the Repository
  
2. Configure the Database
Provide your database configuration in the application.properties file located in src/main/resources/.
  ![image](https://github.com/user-attachments/assets/3ea2bfc0-a604-48c3-9cb1-17bd92d99bf6)

Usage
Logging In
Access the Login Page:
Navigate to http://localhost:3000 to access the frontend of the application.

Login Credentials:

HR Manager:
    Username: hr
    Password: 12345
    
Project Manager:
    Username: pro
    Password: 12345
    
Employee:
    Username: emp
    Password: 12345

    
Features

HR Manager:
- Can navigate to the list of employees, projects, leave requests, and approval requests.
- Can add, edit, and view details of employees.
- Can approve or reject leave requests.
- 
Project Manager:
- Can navigate to the list of employees, projects, leave requests, and approval requests.
- Can add, edit, and view details of projects.
- Can approve or reject leave requests.

- 
Employee:
- Can view their projects and leave requests.
- Can submit leave requests.

Additional Information
- Role-Based Access Control:
    The application implements role-based access control to ensure that only authorized users can access certain functionalities. This is enforced using Spring Security in the backend.
- JWT Authentication:
    Authentication is handled using JSON Web Tokens (JWT). After logging in, a token is stored in local storage and used for subsequent requests to secure endpoints.

![Out of office](https://github.com/user-attachments/assets/5fd0c063-5b31-4499-8c09-0a3e30e3763e)

