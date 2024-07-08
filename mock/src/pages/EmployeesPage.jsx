import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ListItem from '../components/ListItem';
import AddEmployeeForm from '../components/AddEmployeeForm';
import EditEmployeeForm from '../components/EditEmployeeForm';

const EmployeesPage = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showAddForm, setShowAddForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/employees', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        setEmployees(response.data);
        setLoading(false);
      })
      .catch(error => {
        setError(error);
        setLoading(false);
      });
  }, []);

  const updateEmployeeStatus = (id, newStatusId) => {
    const token = localStorage.getItem('token');
    axios.put(`http://localhost:8080/employees/status`, { id, status: newStatusId }, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        setEmployees(employees.map(employee =>
          employee.id === id ? { ...employee, status: { id: newStatusId, title: newStatusId === 1 ? 'Active' : 'Inactive' } } : employee
        ));
      })
      .catch(error => {
        setError(error);
      });
  };

  const handleAddEmployee = (newEmployee) => {
    setEmployees([...employees, newEmployee]);
  };

  const handleUpdateEmployee = (updatedEmployee) => {
    setEmployees(employees.map(employee =>
      employee.id === updatedEmployee.id ? updatedEmployee : employee
    ));
  };

  const filteredEmployees = employees.filter(employee =>
    employee.fullName.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div>
      <h1>Employees</h1>
      <button onClick={() => setShowAddForm(true)}>Add Employee</button>
      <input
        type="text"
        placeholder="Search by name"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      {showAddForm && <AddEmployeeForm onClose={() => setShowAddForm(false)} onAddEmployee={handleAddEmployee} />}
      {showEditForm && <EditEmployeeForm employee={selectedEmployee} onClose={() => setShowEditForm(false)} onUpdateEmployee={handleUpdateEmployee} />}
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Username</th>
            <th>Position</th>
            <th>Status</th>
            <th>Subdivision</th>
            <th>People Partner</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredEmployees.length > 0 ? (
            filteredEmployees.map(employee => (
              <ListItem
                key={employee.id}
                employee={employee}
                onUpdateStatus={updateEmployeeStatus}
                onEdit={() => { setSelectedEmployee(employee); setShowEditForm(true); }}
              />
            ))
          ) : (
            <tr>
              <td colSpan="8">No employees found.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default EmployeesPage;
