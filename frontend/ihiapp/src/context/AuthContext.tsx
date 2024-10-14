// src/context/AuthContext.tsx
import React, { createContext, useState, useContext, ReactNode, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { postAuthenticate } from '../api/Api';
import { jwtDecode } from 'jwt-decode';
import { role } from '../types/Types';

// Define the shape of our context data
interface AuthContextType {
    login: (username: string, password: string) => Promise<void>;
    logout: () => void;
    error: string | null;
    isAuthenticated: boolean;
    sessionId: string | null;
    setIsAuthenticated: (value: boolean) => void; // Add this line
}

// Create the context with default values
const AuthContext = createContext<AuthContextType>({
    login: async () => { },
    logout: () => { },
    error: null,
    isAuthenticated: false,
    sessionId: null,
    setIsAuthenticated: () => { }
});

// Create a provider component
export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [ error, setError ] = useState<string | null>(null);
    const [ isAuthenticated, setIsAuthenticated ] = useState<boolean>(false);
    const [ sessionId, setSessionId ] = useState<string | null>(null);
    const [ userRole, setUserRole ] = useState({})

    const navigate = useNavigate();

    // Check local storage for authentication state on mount
    useEffect(() => {
        const storedIsAuthenticated = localStorage.getItem('isAuthenticated');
        const storedSessionId = localStorage.getItem('sessionId');

        if (storedIsAuthenticated === 'true' && storedSessionId) {
            setIsAuthenticated(true);
            setSessionId(storedSessionId);
        }
    }, []); 

    useEffect(() => {
        console.log('isAuthenticated state changed:', isAuthenticated);
    }, [ isAuthenticated ]);

    const login = async (username: string, password: string) => {
        try {
            const response = await fetch(postAuthenticate, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                const data = await response.json();
                const sessionToken = data.additionalDetails.sessionToken;
                const sessionIdFromBackend = data.additionalDetails.sessionId; // Assuming the backend returns sessionId
                const userIdFromBackend = data.additionalDetails.userId;
                const jwtToken = data.token;  // Get JWT token


                const decodedToken: role = jwtDecode(jwtToken);
                const userRole = decodedToken.role;
                setUserRole(decodedToken)


                // Clear previous session data to avoid any potential conflicts
                localStorage.clear();

                // Store the session token and session ID in localStorage
                localStorage.setItem('session', sessionToken);
                localStorage.setItem('sessionId', sessionIdFromBackend);
                localStorage.setItem('isAuthenticated', 'true');
                localStorage.setItem('userId', userIdFromBackend);
                localStorage.setItem('userRole', userRole);
                localStorage.setItem('jwtToken', jwtToken);

                // Update state and navigate to home
                setIsAuthenticated(true);
                setSessionId(sessionIdFromBackend);
                if (userRole === 'ROLE_ADMIN') {
                    navigate('/admin/dashboard');
                } else if (userRole === 'ROLE_USER') {
                    navigate('/home');
                }
            } else {
                const errorMessage = await response.json();
                throw new Error(errorMessage.message);
            }
        } catch (error) {
            setError(error instanceof Error ? error.message : String(error));
            console.error('Error during login:', error);
        }
    };

    // LOG OUT FUNCTION
    const logout = () => {
        localStorage.removeItem('session');
        localStorage.removeItem('sessionId');
        localStorage.removeItem('userId');
        localStorage.removeItem('userRole');
        localStorage.removeItem('jwtToken');
        localStorage.setItem('isAuthenticated', 'false');
        

        // Update the authentication state
        setIsAuthenticated(false);
        setSessionId(null);

        // Navigate to the login page
        navigate('/');
    };


    useEffect(() => {
        const intervalId = setInterval(async () => {
            const storedSessionId = localStorage.getItem('sessionId');
            const storedUserId = localStorage.getItem('userId');

            if (storedSessionId && storedUserId) {
                try {
                    const sessionResponse = await fetch(`http://localhost:8080/session/${storedSessionId}`);
                    if (!sessionResponse.ok) {
                        const errorData = await sessionResponse.json();
                        console.error('Error fetching session:', errorData.message);
                        localStorage.removeItem('session');
                        localStorage.removeItem('sessionId');
                        localStorage.removeItem('userId');
                        localStorage.removeItem('userRole');
                        localStorage.removeItem('jwtToken');
                        localStorage.setItem('isAuthenticated', 'false');
                        setIsAuthenticated(false);
                        navigate('/'); // Redirect to login page
                        return; // Exit early
                    }

                    const sessionData = await sessionResponse.json();

                    // Check if the session is invalid
                    if (sessionData.status === false) {
                        localStorage.removeItem('session');
                        localStorage.removeItem('sessionId');
                        localStorage.removeItem('userId');
                        localStorage.removeItem('userRole');
                        localStorage.removeItem('jwtToken');
                        localStorage.setItem('isAuthenticated', 'false');
                        setIsAuthenticated(false); // Update authentication state
                        navigate('/'); // Redirect to login page
                    }

                } catch (error) {
                    console.error('Error during session validation:', error);
                    localStorage.removeItem('session');
                    localStorage.removeItem('sessionId');
                    localStorage.removeItem('userId');
                    localStorage.removeItem('userRole');
                    localStorage.removeItem('jwtToken');
                    localStorage.setItem('isAuthenticated', 'false');
                    setIsAuthenticated(false);
                    navigate('/');
                }
            }
        }, 10000); // Check every 30 seconds

        return () => clearInterval(intervalId); // Clean up the interval on component unmount
    }, [ navigate ]);


    return (
        <AuthContext.Provider value={{ login, error, isAuthenticated, sessionId, logout, setIsAuthenticated }}>
            {children}
        </AuthContext.Provider>
    );
};

// Custom hook to use the AuthContext
export const useAuth = () => {
    return useContext(AuthContext);
};
