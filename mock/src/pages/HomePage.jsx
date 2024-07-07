// src/pages/HomePage.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import verifyToken from '../utils/jwtUtils'; // Adjust the path as necessary

const HomePage = () => {
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken = verifyToken(token);
      setUser(decodedToken.sub); // Assuming 'sub' contains the username
      setRole(decodedToken.role); // Assuming 'role' contains the user's role
    } else {
      navigate('/login'); // Redirect to login if no token is found
    }
  }, [navigate]);

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>Welcome, {user}</h1>
      <p>Your role is: {role}</p>
      {role === 'HR_MANAGER' && <div>HR Manager Content</div>}
      {role === 'PROJECT_MANAGER' && <div>Project Manager Content</div>}
      {role === 'EMPLOYEE' && <div>Employee Content</div>}
    </div>
  );
};

export default HomePage;
