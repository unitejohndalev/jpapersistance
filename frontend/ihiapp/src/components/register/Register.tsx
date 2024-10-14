// src/components/Register.tsx

import React, { useState } from 'react';
import { RegisterInfo } from '../../types/Types';
import usePostRegister from '../../customHook/usePostRegister';
import { useNavigate } from 'react-router-dom';


export const RegisterForm = () => {
    const [ registerInfo, setRegisterInfo ] = useState<RegisterInfo>({ email: '', username: '', password: '' });
    const { register, error } = usePostRegister();
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setRegisterInfo({ ...registerInfo, [ name ]: value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); // Prevent the default form submission behavior
        await register(registerInfo.email, registerInfo.username, registerInfo.password);
    };

    const handleLoginClick = () => {
        navigate('/');
    };


    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md p-10 space-y-8 bg-white shadow-md rounded-xl">
                <div className=''>
                    <button onClick={handleLoginClick} 
                            className='px-4 py-2 text-sm font-small text-indigo-600 border border-transparent rounded-md group hover:text-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'>
                        Back to Login
                    </button>
                </div>
                <div className="text-center">
                    <h2 className="mt-6 text-3xl font-bold text-gray-900">
                        Register
                    </h2>
                </div>
                <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
                    <div className="-space-y-px rounded-md shadow-sm">
                        <div>
                            <label htmlFor="email" className="sr-only">
                                Email
                            </label>
                            <input
                                id="email"
                                name="email"
                                type="email"
                                required
                                value={registerInfo.email}
                                onChange={handleChange}
                                className="relative block w-full px-3 py-2 text-gray-900 placeholder-gray-500 border border-gray-300 rounded-none appearance-none rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                                placeholder="Email"
                            />
                        </div>
                        <div>
                            <label htmlFor="username" className="sr-only">
                                Username
                            </label>
                            <input
                                id="username"
                                name="username"
                                type="text"
                                required
                                value={registerInfo.username}
                                onChange={handleChange}
                                className="relative block w-full px-3 py-2 text-gray-900 placeholder-gray-500 border border-gray-300 rounded-none appearance-none rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                                placeholder="Username"
                            />
                        </div>
                        <div>
                            <label htmlFor="password" className="sr-only">
                                Password
                            </label>
                            <input
                                id="password"
                                name="password"
                                type="password"
                                required
                                value={registerInfo.password}
                                onChange={handleChange}
                                className="relative block w-full px-3 py-2 text-gray-900 placeholder-gray-500 border border-gray-300 rounded-none appearance-none rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                                placeholder="Password"
                            />
                        </div>
                    </div>
                    {error && <p className="text-red-500">{error}</p>} {/* Display error message if exists */}

                    <div>
                        <button
                            type="submit"
                            className="relative flex justify-center w-full px-4 py-2 text-sm font-medium text-white bg-indigo-600 border border-transparent rounded-md group hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                        >
                            Sign Up
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};
