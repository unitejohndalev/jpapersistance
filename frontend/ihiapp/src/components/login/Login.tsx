// src/components/LoginForm.tsx
import React, { useState } from 'react';
import { FcGoogle } from "react-icons/fc";
import { SiGithub } from "react-icons/si";
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';




export const LoginForm = () => {
    const [ loginInfo, setLoginInfo ] = useState({ username: '', password: '' });
    const { login, error } = useAuth(); // Access login and error state from context
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setLoginInfo({ ...loginInfo, [ name ]: value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        await login(loginInfo.username, loginInfo.password);
    };

    const handleOAuthLogin = (provider: string) => {
        window.location.href = `http://192.168.40.129:8080/oauth2/authorization/${provider}`;
    };

    const handleRegisterClick = () => {
        navigate('/register');
    };


    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md p-10 space-y-8 bg-white shadow-md rounded-xl">
                <div className="text-center">
                    <h2 className="mt-6 text-3xl font-bold text-gray-900">
                        Log in to your account
                    </h2>
                </div>
                <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
                    <div className="-space-y-px rounded-md shadow-sm">
                        <div>
                            <label htmlFor="username" className="sr-only">
                                Username
                            </label>
                            <input
                                id="username"
                                name="username"
                                type="text"
                                required
                                value={loginInfo.username}
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
                                value={loginInfo.password}
                                onChange={handleChange}
                                className="relative block w-full px-3 py-2 text-gray-900 placeholder-gray-500 border border-gray-300 rounded-none appearance-none rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                                placeholder="Password"
                            />
                        </div>
                    </div>
                    {error && <p className="text-red-500">{error}</p>} {/* Display error message if exists */}

                    <div className='grid grid-cols-1 gap-2'>
                        <button
                            type="submit"
                            className="relative flex justify-center w-full px-4 py-2 text-sm font-medium text-white bg-indigo-600 border border-transparent rounded-md group hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                        >
                            Sign in
                        </button>
                        <button onClick={handleRegisterClick} className="relative flex justify-center w-full px-4 py-2 text-sm font-medium text-white bg-indigo-600 border border-transparent rounded-md group hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Sign Up
                        </button>
                    </div>
                </form>

                <div className='flex items-center justify-start w-full gap-x-5'>
                    <button onClick={() => handleOAuthLogin('github')} className="github-login-btn">
                        <SiGithub className='text-[2rem]' />
                    </button>
                    <button onClick={() => handleOAuthLogin('google')} className="google-login-btn">
                        <FcGoogle className='text-[2rem]'/>
                    </button>
                </div>
            </div>
        </div>
    );
};
