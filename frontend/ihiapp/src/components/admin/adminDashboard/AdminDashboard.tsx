import React from 'react'
import { useAuth } from '../../../context/AuthContext';

const AdminDashboard = () => {
  const { logout } = useAuth();
  return (
    <div>
      <div className='flex justify-end w-full pt-2 pr-2'>
        <button onClick={logout} className='px-2 py-1 border-2 border-red-500 border-solid'>Logout</button>
      </div>
      AdminDashboard</div>
  )
}

export default AdminDashboard