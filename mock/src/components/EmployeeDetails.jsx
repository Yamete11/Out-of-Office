import React from 'react';
import '../styles/CommonForm.css';

const EmployeeDetails = ({ employee, onClose }) => {
  return (
    <div className="details-container">
      <h2>Employee Details</h2>
      <div className="details-content">
        <p><strong>ID:</strong> {employee.id}</p>
        <p><strong>Full Name:</strong> {employee.fullName}</p>
        <p><strong>Username:</strong> {employee.username}</p>
        <p><strong>Position:</strong> {employee.position ? employee.position.title : 'N/A'}</p>
        <p><strong>Status:</strong> {employee.status ? employee.status.title : 'N/A'}</p>
        <p><strong>Subdivision:</strong> {employee.subdivision ? employee.subdivision.title : 'N/A'}</p>
        <p><strong>People Partner:</strong> {employee.peoplePartner ? employee.peoplePartner.fullName : 'N/A'}</p>
      </div>
      <div className="details-buttons">
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default EmployeeDetails;
