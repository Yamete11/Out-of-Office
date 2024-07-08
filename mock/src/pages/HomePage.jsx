import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import verifyToken from '../utils/jwtUtils'; 

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
    <div>
      <h1>Welcome, {user}</h1>
      <p>Your role is: {role}</p>
      {role === 'HR_MANAGER' && (
        <div>
          <button onClick={() => handleNavigation('/employees')}>Employees</button>
          <button onClick={() => handleNavigation('/projects')}>Projects</button>
          <button onClick={() => handleNavigation('/leave-requests')}>Leave Requests</button>
          <button onClick={() => handleNavigation('/approval-requests')}>Approval Requests</button>
        </div>
      )}
      {role === 'PROJECT_MANAGER' && <div>Project Manager Content</div>}
      {role === 'EMPLOYEE' && <div>Employee Content</div>}
    </div>
  );
};

export default HomePage;
