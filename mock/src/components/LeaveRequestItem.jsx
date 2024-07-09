import React from 'react';

const LeaveRequestItem = ({ request, onDetailsClick }) => {
  return (
    <tr>
      <td>{request.employee?.fullName || 'N/A'}</td>
      <td>{request.absenceReason?.title || 'N/A'}</td>
      <td>{request.startDate ? new Date(request.startDate).toLocaleDateString() : 'N/A'}</td>
      <td>{request.endDate ? new Date(request.endDate).toLocaleDateString() : 'N/A'}</td>
      <td>{request.requestStatus?.title || 'N/A'}</td>
      <td>{request.comment || 'N/A'}</td>
      <td>
        <button onClick={onDetailsClick}>
          Details
        </button>
      </td>
    </tr>
  );
};

export default LeaveRequestItem;
