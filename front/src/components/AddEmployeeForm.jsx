import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/CommonForm.css';

const AddEmployeeForm = ({ onClose, onAddEmployee }) => {
  const [formData, setFormData] = useState({
    fullName: '',
    username: '',
    password: '',
    outOfOfficeBalance: 0,
    position: '',
    status: '',
    subdivision: '',
    peoplePartner: ''
  });
  const [positions, setPositions] = useState([]);
  const [statuses, setStatuses] = useState([]);
  const [subdivisions, setSubdivisions] = useState([]);
  const [peoplePartners, setPeoplePartners] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem('token');
    
    const fetchData = async () => {
      try {
        const [positionsResponse, statusesResponse, subdivisionsResponse, peoplePartnersResponse] = await Promise.all([
          axios.get('http://localhost:8080/positions', { headers: { Authorization: `Bearer ${token}` } }),
          axios.get('http://localhost:8080/statuses', { headers: { Authorization: `Bearer ${token}` } }),
          axios.get('http://localhost:8080/subdivisions', { headers: { Authorization: `Bearer ${token}` } }),
          axios.get('http://localhost:8080/employees/hr', { headers: { Authorization: `Bearer ${token}` } })
        ]);

        setPositions(positionsResponse.data);
        setStatuses(statusesResponse.data);
        setSubdivisions(subdivisionsResponse.data);
        setPeoplePartners(peoplePartnersResponse.data);
      } catch (error) {
        console.error('Error fetching data', error);
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

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');

    axios.post('http://localhost:8080/employees', formData, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        onAddEmployee(response.data);
        onClose();
      })
      .catch(error => {
        console.error('There was an error creating the employee!', error);
      });
  };

  return (
    <div className="form-container">
      <h2>Add Employee</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Full Name:</label>
          <input type="text" name="fullName" value={formData.fullName} onChange={handleInputChange} required />
        </div>
        <div className="form-group">
          <label>Username:</label>
          <input type="text" name="username" value={formData.username} onChange={handleInputChange} required />
        </div>
        <div className="form-group">
          <label>Password:</label>
          <input type="password" name="password" value={formData.password} onChange={handleInputChange} required />
        </div>
        <div className="form-group">
          <label>Out Of Office Balance:</label>
          <input type="number" name="outOfOfficeBalance" value={formData.outOfOfficeBalance} onChange={handleInputChange} required />
        </div>
        <div className="form-group">
          <label>Position:</label>
          <select name="position" value={formData.position} onChange={handleInputChange} required>
            <option value="" disabled>Select position</option>
            {positions.map(pos => (
              <option key={pos.id} value={pos.id}>{pos.title}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Status:</label>
          <select name="status" value={formData.status} onChange={handleInputChange} required>
            <option value="" disabled>Select status</option>
            {statuses.map(stat => (
              <option key={stat.id} value={stat.id}>{stat.title}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Subdivision:</label>
          <select name="subdivision" value={formData.subdivision} onChange={handleInputChange} required>
            <option value="" disabled>Select subdivision</option>
            {subdivisions.map(sub => (
              <option key={sub.id} value={sub.id}>{sub.title}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>People Partner:</label>
          <select name="peoplePartner" value={formData.peoplePartner} onChange={handleInputChange}>
            <option value="">None</option>
            {peoplePartners.map(partner => (
              <option key={partner.id} value={partner.id}>{partner.fullName}</option>
            ))}
          </select>
        </div>
        <div className="form-buttons">
          <button type="submit">Add Employee</button>
          <button type="button" onClick={onClose}>Cancel</button>
        </div>
      </form>
    </div>
  );
};

export default AddEmployeeForm;
