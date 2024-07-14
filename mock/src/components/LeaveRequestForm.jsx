import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LeaveRequestForm = ({ request, onClose, onSave }) => {
  const [formData, setFormData] = useState({
    employee: request?.employee.id || '',
    absenceReason: request?.absenceReason.id || '',
    startDate: request?.startDate || '',
    endDate: request?.endDate || '',
    comment: request?.comment || '',
  });
  const [absenceReasons, setAbsenceReasons] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch absence reasons and employees
    const fetchData = async () => {
      try {
        const token = localStorage.getItem('token');
        const [absenceResponse, employeesResponse] = await Promise.all([
          axios.get('http://localhost:8080/absenceReasons', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }),
          axios.get('http://localhost:8080/employees', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })
        ]);
        setAbsenceReasons(absenceResponse.data);
        setEmployees(employeesResponse.data);
      } catch (error) {
        setError(error);
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    try {
      let response;
      if (request) {
        // Update request
        response = await axios.put(`http://localhost:8080/leaverequests/${request.id}`, formData, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
      } else {
        // Create new request
        response = await axios.post('http://localhost:8080/leaverequests', formData, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
      }
      onSave(response.data);
    } catch (error) {
      setError(error);
      console.error('Error saving data:', error);
    }
  };

  return (
    <div className="leave-request-form">
      <h2>{request ? 'Update' : 'Create'} Leave Request</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Employee:</label>
          <select name="employee" value={formData.employee} onChange={handleInputChange}>
            <option value="">Select Employee</option>
            {employees.map(employee => (
              <option key={employee.id} value={employee.id}>{employee.fullName}</option>
            ))}
          </select>
        </div>
        <div>
          <label>Absence Reason:</label>
          <select name="absenceReason" value={formData.absenceReason} onChange={handleInputChange}>
            <option value="">Select Reason</option>
            {absenceReasons.map(reason => (
              <option key={reason.id} value={reason.id}>{reason.title}</option>
            ))}
          </select>
        </div>
        <div>
          <label>Start Date:</label>
          <input type="date" name="startDate" value={formData.startDate} onChange={handleInputChange} />
        </div>
        <div>
          <label>End Date:</label>
          <input type="date" name="endDate" value={formData.endDate} onChange={handleInputChange} />
        </div>
        <div>
          <label>Comment:</label>
          <input type="text" name="comment" value={formData.comment} onChange={handleInputChange} />
        </div>
        <button type="submit">{request ? 'Update' : 'Create'} Request</button>
      </form>
      <button onClick={onClose}>Close</button>
      {error && <p style={{ color: 'red' }}>Error: {error.message}</p>}
    </div>
  );
};

export default LeaveRequestForm;
