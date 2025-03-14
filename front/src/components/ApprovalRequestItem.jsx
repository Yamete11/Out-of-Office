import React from 'react';

const ApprovalRequestItem = ({ request, onDetailsClick }) => {
  return (
    <tr>
      <td>{request.id || 'N/A'}</td>
      <td>{request.approver?.fullName || 'N/A'}</td>
      <td>{request.leaveRequest?.reason || 'N/A'}</td>
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

export default ApprovalRequestItem;
