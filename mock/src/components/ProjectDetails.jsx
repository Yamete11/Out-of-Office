import React from 'react';
import '../styles/CommonForm.css';

const ProjectDetails = ({ project, onClose }) => {
  return (
    <div className="details-container">
      <h2>Project Details</h2>
      <div className="details-content">
        <p><strong>Project ID:</strong> {project.id}</p>
        <p><strong>Project Type:</strong> {project.projectType?.title || 'N/A'}</p>
        <p><strong>Start Date:</strong> {project.startDate ? new Date(project.startDate).toLocaleDateString() : 'N/A'}</p>
        <p><strong>End Date:</strong> {project.endDate ? new Date(project.endDate).toLocaleDateString() : 'N/A'}</p>
        <p><strong>Project Manager:</strong> {project.projectManager?.fullName || 'N/A'}</p>
        <p><strong>Comment:</strong> {project.comment || 'N/A'}</p>
        <p><strong>Status:</strong> {project.status?.title || 'N/A'}</p>
      </div>
      <div className="details-buttons">
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default ProjectDetails;
