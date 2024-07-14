import React from 'react';
import '../styles/CommonForm.css';

const LeaveRequestDetails = ({ request, onClose }) => {
  return (
    <div className="details-container">
      <h2>Leave Request Details</h2>
      <div className="details-content">
        <p><strong>Employee:</strong> {request.employee?.fullName || 'N/A'}</p>
        <p><strong>Absence Reason:</strong> {request.absenceReason?.title || 'N/A'}</p>
        <p><strong>Start Date:</strong> {request.startDate ? new Date(request.startDate).toLocaleDateString() : 'N/A'}</p>
        <p><strong>End Date:</strong> {request.endDate ? new Date(request.endDate).toLocaleDateString() : 'N/A'}</p>
        <p><strong>Request Status:</strong> {request.requestStatus?.title || 'N/A'}</p>
        <p><strong>Comment:</strong> {request.comment || 'N/A'}</p>
      </div>
      <div className="details-buttons">
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default LeaveRequestDetails;
