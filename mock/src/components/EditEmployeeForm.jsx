import React, { useState, useEffect } from 'react';
import axios from 'axios';

const EditEmployeeForm = ({ employee, onClose, onUpdateEmployee }) => {
  const [fullName, setFullName] = useState(employee.fullName);
  const [username, setUsername] = useState(employee.username);
  const [password, setPassword] = useState('');
  const [outOfOfficeBalance, setOutOfOfficeBalance] = useState(employee.outOfOfficeBalance);
  const [position, setPosition] = useState(employee.position ? employee.position.id : '');
  const [status, setStatus] = useState(employee.status ? employee.status.id : '');
  const [subdivision, setSubdivision] = useState(employee.subdivision ? employee.subdivision.id : '');
  const [peoplePartner, setPeoplePartner] = useState(employee.peoplePartner ? employee.peoplePartner.id : '');
  // const [photo, setPhoto] = useState(null); // Commented out photo handling
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

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');

    const employeeData = {
      id: employee.id,
      fullName,
      username,
      outOfOfficeBalance,
      position,
      status,
      subdivision,
      peoplePartner: peoplePartner || null
    };

    console.log(employeeData)

    axios.put(`http://localhost:8080/employees/employee`, employeeData, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        onUpdateEmployee(response.data);
        onClose();
      })
      .catch(error => {
        console.error('There was an error updating the employee!', error);
      });
  };

  return (
    <div>
      <h2>Edit Employee</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Full Name:</label>
          <input type="text" value={fullName} onChange={(e) => setFullName(e.target.value)} required />
        </div>
        <div>
          <label>Username:</label>
          <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
        </div>
        <div>
          <label>Out Of Office Balance:</label>
          <input type="number" value={outOfOfficeBalance} onChange={(e) => setOutOfOfficeBalance(e.target.value)} required />
        </div>
        <div>
          <label>Position:</label>
          <select value={position} onChange={(e) => setPosition(e.target.value)} required>
            <option value="" disabled>Select position</option>
            {positions.map(pos => (
              <option key={pos.id} value={pos.id}>{pos.title}</option>
            ))}
          </select>
        </div>
        <div>
          <label>Status:</label>
          <select value={status} onChange={(e) => setStatus(e.target.value)} required>
            <option value="" disabled>Select status</option>
            {statuses.map(stat => (
              <option key={stat.id} value={stat.id}>{stat.title}</option>
            ))}
          </select>
        </div>
        <div>
          <label>Subdivision:</label>
          <select value={subdivision} onChange={(e) => setSubdivision(e.target.value)} required>
            <option value="" disabled>Select subdivision</option>
            {subdivisions.map(sub => (
              <option key={sub.id} value={sub.id}>{sub.title}</option>
            ))}
          </select>
        </div>
        <div>
          <label>People Partner:</label>
          <select value={peoplePartner} onChange={(e) => setPeoplePartner(e.target.value)}>
            <option value="">None</option>
            {peoplePartners.map(partner => (
              <option key={partner.id} value={partner.id}>{partner.fullName}</option>
            ))}
          </select>
        </div>
        {/* <div>
          <label>Photo:</label>
          <input type="file" onChange={(e) => setPhoto(e.target.files[0])} />
        </div> */}
        <button type="submit">Update Employee</button>
        <button type="button" onClick={onClose}>Cancel</button>
      </form>
    </div>
  );
};

export default EditEmployeeForm;
