import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ApprovalRequestItem from '../components/ApprovalRequestItem';
import ApprovalRequestDetails from '../components/ApprovalRequestDetails';

const ApprovalRequestsPage = () => {
  const [approvalRequests, setApprovalRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [sortBy, setSortBy] = useState('id');
  const [sortOrder, setSortOrder] = useState('asc');

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/approvalrequest', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        setApprovalRequests(response.data);
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

  const sortedRequests = sortData([...approvalRequests], sortBy, sortOrder);

  const filteredRequests = sortedRequests.filter(request =>
    request.id?.toString().toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div>
      <h1>Approval Requests</h1>
      <input
        type="text"
        placeholder="Search by approval request ID"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <table>
        <thead>
          <tr>
            <th>
              ID
              <button onClick={() => handleSort('id')}>{sortOrder === 'asc' && sortBy === 'id' ? '↑' : '↓'}</button>
            </th>
            <th>
              Approver
              <button onClick={() => handleSort('approver.fullName')}>{sortOrder === 'asc' && sortBy === 'approver.fullName' ? '↑' : '↓'}</button>
            </th>
            <th>
              Leave Request
              <button onClick={() => handleSort('leaveRequest.reason')}>{sortOrder === 'asc' && sortBy === 'leaveRequest.reason' ? '↑' : '↓'}</button>
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
              <ApprovalRequestItem
                key={request.id}
                request={request}
                onDetailsClick={() => setSelectedRequest(request)}
              />
            ))
          ) : (
            <tr>
              <td colSpan="6">No approval requests found.</td>
            </tr>
          )}
        </tbody>
      </table>
      {selectedRequest && (
        <ApprovalRequestDetails 
          request={selectedRequest} 
          onClose={() => setSelectedRequest(null)} 
        />
      )}
    </div>
  );
};

export default ApprovalRequestsPage;
