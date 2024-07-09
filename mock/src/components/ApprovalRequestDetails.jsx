import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ApprovalRequestDetails = ({ request, onClose }) => {
  const [comment, setComment] = useState('');
  const [statuses, setStatuses] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch all possible statuses
    const fetchStatuses = async () => {
      try {
        const response = await axios.get('http://localhost:8080/requestStatuses', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });
        setStatuses(response.data);
      } catch (error) {
        console.error('Error fetching statuses:', error);
      }
    };

    fetchStatuses();
  }, []);

  const changeStatus = (statusId) => {
    const token = localStorage.getItem('token');
    axios.put('http://localhost:8080/approvalrequest/changeStatus', {
      id: request.id,
      status: statusId,
      comment: statusId === 3 ? comment : '' // Only send comment if rejecting
    }, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        alert(statusId === 2 ? 'Request approved.' : 'Request rejected.');
        onClose();
      })
      .catch(error => {
        setError(error);
        alert('Error processing the request.');
      });
  };

  const handleApprove = () => {
    const approvedStatus = statuses.find(status => status.title === 'Approved');
    if (approvedStatus) {
      changeStatus(approvedStatus.id);
    }
  };

  const handleReject = () => {
    const rejectedStatus = statuses.find(status => status.title === 'Rejected');
    if (rejectedStatus) {
      changeStatus(rejectedStatus.id);
    }
  };

  return (
    <div className="approval-request-details">
      <h2>Approval Request Details</h2>
      <p><strong>ID:</strong> {request.id || 'N/A'}</p>
      <p><strong>Approver:</strong> {request.approver?.fullName || 'N/A'}</p>
      <p><strong>Leave Request Reason:</strong> {request.leaveRequest?.reason || 'N/A'}</p>
      <p><strong>Request Status:</strong> {request.requestStatus?.title || 'N/A'}</p>
      <p><strong>Comment:</strong> {request.comment || 'N/A'}</p>
      {request.requestStatus?.title === 'New' && (
        <>
          <input 
            type="text" 
            placeholder="Rejection Comment" 
            value={comment} 
            onChange={(e) => setComment(e.target.value)} 
          />
          <div>
            <button onClick={handleApprove}>Approve</button>
            <button onClick={handleReject}>Reject</button>
          </div>
        </>
      )}
      <button onClick={onClose}>Close</button>
      {error && <p style={{ color: 'red' }}>Error: {error.message}</p>}
    </div>
  );
};

export default ApprovalRequestDetails;
