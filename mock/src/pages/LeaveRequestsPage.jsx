import React, { useEffect, useState } from 'react';
import axios from 'axios';
import LeaveRequestItem from '../components/LeaveRequestItem';

const LeaveRequestsPage = () => {
  const [leaveRequests, setLeaveRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/leaverequests', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        setLeaveRequests(response.data);
        setLoading(false);
      })
      .catch(error => {
        setError(error);
        setLoading(false);
      });
  }, []);

  const filteredRequests = leaveRequests.filter(request =>
    request.employee?.fullName?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div>
      <h1>Leave Requests</h1>
      <input
        type="text"
        placeholder="Search by employee name"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <table>
        <thead>
          <tr>
            <th>Employee</th>
            <th>Absence Reason</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Request Status</th>
            <th>Comment</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredRequests.length > 0 ? (
            filteredRequests.map(request => (
              <LeaveRequestItem
                key={request.id}
                request={request}
              />
            ))
          ) : (
            <tr>
              <td colSpan="7">No leave requests found.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default LeaveRequestsPage;
