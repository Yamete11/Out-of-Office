import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ProjectItem from '../components/ProjectItem';
import ProjectDetails from '../components/ProjectDetails';

const ProjectsPage = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedProject, setSelectedProject] = useState(null);
  const [sortBy, setSortBy] = useState('id');
  const [sortOrder, setSortOrder] = useState('asc');

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

  const sortedProjects = sortData([...projects], sortBy, sortOrder);

  const filteredProjects = sortedProjects.filter(project =>
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
            <th>
              Project ID
              <button onClick={() => handleSort('id')}>{sortOrder === 'asc' && sortBy === 'id' ? '↑' : '↓'}</button>
            </th>
            <th>
              Project Type
              <button onClick={() => handleSort('projectType.title')}>{sortOrder === 'asc' && sortBy === 'projectType.title' ? '↑' : '↓'}</button>
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
              Project Manager
              <button onClick={() => handleSort('projectManager.fullName')}>{sortOrder === 'asc' && sortBy === 'projectManager.fullName' ? '↑' : '↓'}</button>
            </th>
            <th>
              Comment
              <button onClick={() => handleSort('comment')}>{sortOrder === 'asc' && sortBy === 'comment' ? '↑' : '↓'}</button>
            </th>
            <th>
              Status
              <button onClick={() => handleSort('status.title')}>{sortOrder === 'asc' && sortBy === 'status.title' ? '↑' : '↓'}</button>
            </th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredProjects.length > 0 ? (
            filteredProjects.map(project => (
              <ProjectItem
                key={project.id}
                project={project}
                onDetailsClick={() => setSelectedProject(project)}
              />
            ))
          ) : (
            <tr>
              <td colSpan="8">No projects found.</td>
            </tr>
          )}
        </tbody>
      </table>
      {selectedProject && (
        <ProjectDetails 
          project={selectedProject} 
          onClose={() => setSelectedProject(null)} 
        />
      )}
    </div>
  );
};

export default ProjectsPage;
