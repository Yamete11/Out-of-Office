import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../styles/CommonForm.css';

const EditLeaveRequestForm = ({ request, onClose, onUpdateLeaveRequest }) => {
  const formatDateForInput = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const [formData, setFormData] = useState({
    employee: request.employee.id,
    absenceReason: request.absenceReason.id,
    startDate: formatDateForInput(request.startDate),
    endDate: formatDateForInput(request.endDate),
    comment: request.comment || '',
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
      const response = await axios.put(`http://localhost:8080/leaverequests/${request.id}`, formData, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      onUpdateLeaveRequest(response.data);
      onClose();
    } catch (error) {
      setError(error);
      console.error('Error updating leave request:', error);
    }
  };

  return (
    <div className="form-container">
      <h2>Edit Leave Request</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Employee:</label>
          <select name="employee" value={formData.employee} onChange={handleInputChange} disabled>
            {employees.map(employee => (
              <option key={employee.id} value={employee.id}>{employee.fullName}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Absence Reason:</label>
          <select name="absenceReason" value={formData.absenceReason} onChange={handleInputChange}>
            <option value="" disabled>Select Reason</option>
            {absenceReasons.map(reason => (
              <option key={reason.id} value={reason.id}>{reason.title}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Start Date:</label>
          <input type="date" name="startDate" value={formData.startDate} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>End Date:</label>
          <input type="date" name="endDate" value={formData.endDate} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Comment:</label>
          <input type="text" name="comment" value={formData.comment} onChange={handleInputChange} />
        </div>
        <div className="form-buttons">
          <button type="submit">Update Request</button>
          <button type="button" onClick={onClose}>Cancel</button>
        </div>
      </form>
      {error && <p style={{ color: 'red' }}>Error: {error.message}</p>}
    </div>
  );
};

export default EditLeaveRequestForm;
