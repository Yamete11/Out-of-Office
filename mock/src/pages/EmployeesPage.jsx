import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ListItem from '../components/ListItem';
import AddEmployeeForm from '../components/AddEmployeeForm';
import EditEmployeeForm from '../components/EditEmployeeForm';
import EmployeeDetails from '../components/EmployeeDetails';

const EmployeesPage = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showAddForm, setShowAddForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [sortBy, setSortBy] = useState('fullName');
  const [sortOrder, setSortOrder] = useState('asc');

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

  const handleSort = (column) => {
    const order = sortBy === column && sortOrder === 'asc' ? 'desc' : 'asc';
    setSortBy(column);
    setSortOrder(order);
  };

  const sortData = (data, column, order) => {
    return data.sort((a, b) => {
      const aValue = column.includes('.') ? column.split('.').reduce((o, i) => o[i], a) : a[column];
      const bValue = column.includes('.') ? column.split('.').reduce((o, i) => o[i], b) : b[column];
      if (aValue < bValue) return order === 'asc' ? -1 : 1;
      if (aValue > bValue) return order === 'asc' ? 1 : -1;
      return 0;
    });
  };

  const sortedEmployees = sortData([...employees], sortBy, sortOrder);

  const filteredEmployees = sortedEmployees.filter(employee =>
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
            <th>
              ID
              <button onClick={() => handleSort('id')}>{sortOrder === 'asc' && sortBy === 'id' ? '↑' : '↓'}</button>
            </th>
            <th>
              Full Name
              <button onClick={() => handleSort('fullName')}>{sortOrder === 'asc' && sortBy === 'fullName' ? '↑' : '↓'}</button>
            </th>
            <th>
              Username
              <button onClick={() => handleSort('username')}>{sortOrder === 'asc' && sortBy === 'username' ? '↑' : '↓'}</button>
            </th>
            <th>
              Position
              <button onClick={() => handleSort('position.title')}>{sortOrder === 'asc' && sortBy === 'position.title' ? '↑' : '↓'}</button>
            </th>
            <th>
              Status
              <button onClick={() => handleSort('status.title')}>{sortOrder === 'asc' && sortBy === 'status.title' ? '↑' : '↓'}</button>
            </th>
            <th>
              Subdivision
              <button onClick={() => handleSort('subdivision.title')}>{sortOrder === 'asc' && sortBy === 'subdivision.title' ? '↑' : '↓'}</button>
            </th>
            <th>
              People Partner
            </th>
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
                onViewDetails={() => setSelectedEmployee(employee)}
              />
            ))
          ) : (
            <tr>
              <td colSpan="8">No employees found.</td>
            </tr>
          )}
        </tbody>
      </table>
      {selectedEmployee && !showEditForm && (
        <EmployeeDetails 
          employee={selectedEmployee} 
          onClose={() => setSelectedEmployee(null)} 
        />
      )}
    </div>
  );
};

export default EmployeesPage;
