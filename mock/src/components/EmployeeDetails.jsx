import React from 'react';

const EmployeeDetails = ({ employee, onClose }) => {
  return (
    <div className="employee-details">
      <h2>Employee Details</h2>
      <p><strong>ID:</strong> {employee.id}</p>
      <p><strong>Full Name:</strong> {employee.fullName}</p>
      <p><strong>Username:</strong> {employee.username}</p>
      <p><strong>Position:</strong> {employee.position ? employee.position.title : 'N/A'}</p>
      <p><strong>Status:</strong> {employee.status ? employee.status.title : 'N/A'}</p>
      <p><strong>Subdivision:</strong> {employee.subdivision ? employee.subdivision.title : 'N/A'}</p>
      <p><strong>People Partner:</strong> {employee.peoplePartner ? employee.peoplePartner.fullName : 'N/A'}</p>
      <button onClick={onClose}>Close</button>
    </div>
  );
};

export default EmployeeDetails;
