import React, { useEffect, useState } from 'react';
import axios from 'axios';
import LeaveRequestItem from '../components/LeaveRequestItem';
import LeaveRequestDetails from '../components/LeaveRequestDetails';
import EditLeaveRequestForm from '../components/EditLeaveRequestForm';
import AddLeaveRequestForm from '../components/AddLeaveRequestForm';
import '../styles/CommonPage.css';

const LeaveRequestsPage = () => {
  const [leaveRequests, setLeaveRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [sortBy, setSortBy] = useState('employee.fullName');
  const [sortOrder, setSortOrder] = useState('asc');
  const [showEditForm, setShowEditForm] = useState(false);
  const [showAddForm, setShowAddForm] = useState(false);

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

  const handleEdit = (id) => {
    const request = leaveRequests.find(req => req.id === id);
    setSelectedRequest(request);
    setShowEditForm(true);
  };

  const handleDetails = (id) => {
    const request = leaveRequests.find(req => req.id === id);
    setSelectedRequest(request);
  };

  const handleSubmit = (id) => {
    const token = localStorage.getItem('token');
    axios.put(`http://localhost:8080/leaverequests/${id}/submit`, {}, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        setLeaveRequests(leaveRequests.map(request =>
          request.id === id ? response.data : request
        ));
        alert('Leave request submitted successfully.');
      })
      .catch(error => {
        setError(error);
        alert('Error submitting the leave request.');
      });
  };

  const handleAddRequest = (newRequest) => {
    setLeaveRequests([...leaveRequests, newRequest]);
    setShowAddForm(false);
  };

  const handleUpdateRequest = (updatedRequest) => {
    setLeaveRequests(leaveRequests.map(request =>
      request.id === updatedRequest.id ? updatedRequest : request
    ));
    setShowEditForm(false);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div className="page-container">
      <h1>Leave Requests</h1>
      <button onClick={() => setShowAddForm(true)}>Add Leave Request</button>
      <input
        type="text"
        placeholder="Search by employee name"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      {showAddForm && <AddLeaveRequestForm onClose={() => setShowAddForm(false)} onAddLeaveRequest={handleAddRequest} />}
      {showEditForm && selectedRequest && (
        <EditLeaveRequestForm
          request={selectedRequest}
          onClose={() => setShowEditForm(false)}
          onUpdateLeaveRequest={handleUpdateRequest}
        />
      )}
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
                onEdit={handleEdit}
                onDetails={handleDetails}
                onSubmit={handleSubmit}
              />
            ))
          ) : (
            <tr>
              <td colSpan="7">No leave requests found.</td>
            </tr>
          )}
        </tbody>
      </table>
      {selectedRequest && !showEditForm && (
        <LeaveRequestDetails 
          request={selectedRequest} 
          onClose={() => setSelectedRequest(null)} 
        />
      )}
    </div>
  );
};

export default LeaveRequestsPage;
