// src/hooks/usePostRegister.ts

import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { postRegister } from '../api/Api';

const usePostRegister = () => {
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const register = async (email: string, username: string, password: string) => {
        try {
            const response = await fetch(postRegister, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, username, password }),
            });

            if (response.ok) {
                const data = await response.json(); // Parse JSON response
                const sessionToken = data.sessionToken; // Access sessionToken directly
                localStorage.setItem('session', sessionToken);
                localStorage.setItem('isAuthenticated', 'true');
                console.log('Register successfully, [TODO]session token stored.');
                navigate(`/`);
            } else {
                const errorMessage = await response.json(); // Parse error response as JSON
                throw new Error(errorMessage.message); // Throw error for handling
            }
        } catch (error) {
            setError(error instanceof Error ? error.message : String(error));
            console.error('Error during registration:', error);
        }
    };

    return { register, error };
};

export default usePostRegister;
