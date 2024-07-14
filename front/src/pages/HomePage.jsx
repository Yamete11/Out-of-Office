import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import verifyToken from '../utils/jwtUtils'; 
import '../styles/CommonForm.css'; // Importing the common CSS for consistency

const HomePage = () => {
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken = verifyToken(token);
      setUser(decodedToken.sub);
      setRole(decodedToken.role); 
    } else {
      navigate('/login');
    }
  }, [navigate]);

  const handleNavigation = (path) => {
    navigate(path);
  };

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="home-page">
      <h1>Welcome, {user}</h1>
      <p>Your role is: {role}</p>
      {(role === 'HR_MANAGER' || role === 'PROJECT_MANAGER') && (
        <div className="navigation-buttons">
          <button onClick={() => handleNavigation('/employees')}>Employees</button>
          <button onClick={() => handleNavigation('/projects')}>Projects</button>
          <button onClick={() => handleNavigation('/leave-requests')}>Leave Requests</button>
          <button onClick={() => handleNavigation('/approval-requests')}>Approval Requests</button>
        </div>
      )}
      {role === 'EMPLOYEE' && (
        <div className="navigation-buttons">
          <button onClick={() => handleNavigation('/projects')}>My Projects</button>
          <button onClick={() => handleNavigation('/leave-requests')}>My Leave Requests</button>
        </div>
      )}
    </div>
  );
};

export default HomePage;
