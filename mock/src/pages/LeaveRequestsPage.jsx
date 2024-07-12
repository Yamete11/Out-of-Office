import React, { useEffect, useState } from 'react';
import axios from 'axios';
import LeaveRequestItem from '../components/LeaveRequestItem';
import LeaveRequestDetails from '../components/LeaveRequestDetails';

const LeaveRequestsPage = () => {
  const [leaveRequests, setLeaveRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [sortBy, setSortBy] = useState('employee.fullName');
  const [sortOrder, setSortOrder] = useState('asc');

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

  const sortedRequests = sortData([...leaveRequests], sortBy, sortOrder);

  const filteredRequests = sortedRequests.filter(request =>
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
            <th>
              Employee
              <button onClick={() => handleSort('employee.fullName')}>{sortOrder === 'asc' && sortBy === 'employee.fullName' ? '↑' : '↓'}</button>
            </th>
            <th>
              Absence Reason
              <button onClick={() => handleSort('absenceReason.title')}>{sortOrder === 'asc' && sortBy === 'absenceReason.title' ? '↑' : '↓'}</button>
            </th>
            <th>
              Start Date
              <button onClick={() => handleSort('startDate')}>{sortOrder === 'asc' && sortBy === 'startDate' ? '↑' : '↓'}</button>
            </th>
            <th>
              End Date
              <button onClick={() => handleSort('endDate')}>{sortOrder === 'asc' && sortBy === 'endDate' ? '↑' : '↓'}</button>
            </th>
            <th>
              Request Status
              <button onClick={() => handleSort('requestStatus.title')}>{sortOrder === 'asc' && sortBy === 'requestStatus.title' ? '↑' : '↓'}</button>
            </th>
            <th>
              Comment
              <button onClick={() => handleSort('comment')}>{sortOrder === 'asc' && sortBy === 'comment' ? '↑' : '↓'}</button>
            </th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredRequests.length > 0 ? (
            filteredRequests.map(request => (
              <LeaveRequestItem
                key={request.id}
                request={request}
                onDetailsClick={() => setSelectedRequest(request)}
              />
            ))
          ) : (
            <tr>
              <td colSpan="7">No leave requests found.</td>
            </tr>
          )}
        </tbody>
      </table>
      {selectedRequest && (
        <LeaveRequestDetails 
          request={selectedRequest} 
          onClose={() => setSelectedRequest(null)} 
        />
      )}
    </div>
  );
};

export default LeaveRequestsPage;
