import React from 'react';

const ProjectItem = ({ project, onDetailsClick }) => {
  return (
    <tr>
      <td>{project.id || 'N/A'}</td>
      <td>{project.projectType?.title || 'N/A'}</td>
      <td>{project.startDate ? new Date(project.startDate).toLocaleDateString() : 'N/A'}</td>
      <td>{project.endDate ? new Date(project.endDate).toLocaleDateString() : 'N/A'}</td>
      <td>{project.projectManager?.fullName || 'N/A'}</td>
      <td>{project.comment || 'N/A'}</td>
      <td>{project.status?.title || 'N/A'}</td>
      <td>
        <button onClick={onDetailsClick}>
          Details
        </button>
      </td>
    </tr>
  );
};

export default ProjectItem;
