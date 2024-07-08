import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ProjectItem from '../components/ProjectItem';

const ProjectsPage = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/projects', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        setProjects(response.data);
        setLoading(false);
      })
      .catch(error => {
        setError(error);
        setLoading(false);
      });
  }, []);

  const filteredProjects = projects.filter(project =>
    project.id?.toString().toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div>
      <h1>Projects</h1>
      <input
        type="text"
        placeholder="Search by project ID"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <table>
        <thead>
          <tr>
            <th>Project ID</th>
            <th>Project Type</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Project Manager</th>
            <th>Comment</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredProjects.length > 0 ? (
            filteredProjects.map(project => (
              <ProjectItem
                key={project.id}
                project={project}
              />
            ))
          ) : (
            <tr>
              <td colSpan="8">No projects found.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ProjectsPage;
