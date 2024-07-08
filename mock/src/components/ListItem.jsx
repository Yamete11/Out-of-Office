import React from 'react';

const ListItem = ({ employee, onUpdateStatus, onEdit }) => {
  const handleStatusChange = () => {
    const newStatusId = employee.status.id === 1 ? 2 : 1;
    onUpdateStatus(employee.id, newStatusId);
  };

  return (
    <tr>
      <td>{employee.id}</td>
      <td>{employee.fullName}</td>
      <td>{employee.username}</td>
      <td>{employee.position ? employee.position.title : 'N/A'}</td>
      <td>{employee.status ? employee.status.title : 'N/A'}</td>
      <td>{employee.subdivision ? employee.subdivision.title : 'N/A'}</td>
      <td>{employee.peoplePartner ? employee.peoplePartner.fullName : 'N/A'}</td>
      <td>
        <button onClick={onEdit}>
          Edit
        </button>
        <button onClick={handleStatusChange}>
          {employee.status.id === 1 ? 'Deactivate' : 'Activate'}
        </button>
      </td>
    </tr>
  );
};

export default ListItem;
