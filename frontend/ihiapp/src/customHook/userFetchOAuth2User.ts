import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';

// Define the User interface to include session data and userId
interface User {
    username: string | null;
    email: string | null;
    sessionToken: string | null;
    sessionId: string | null;
    userId: string | null; // Add userId to the User interface
}

const useFetchOAuth2User = () => {
    const [ user, setUser ] = useState<User | null>(null); // Track the user state
    const [ loading, setLoading ] = useState(true); // Track loading state
    const [ error, setError ] = useState<string | null>(null); // Track error state

    const { setIsAuthenticated } = useAuth();

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch('http://192.168.40.129:8080/home', {
                    credentials: 'include', // Include cookies for authentication
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch user data');
                }

                const userData = await response.json(); // Parse the response as JSON
                console.log(userData);

                // Extract session token, session ID, and authentication status from response
                const { username, email, sessionToken, sessionId, userRole, userId } = userData;

                // Store session token, session ID, userId, and other info in local storage
                localStorage.setItem('sessionToken', sessionToken);
                localStorage.setItem('sessionId', sessionId);
                localStorage.setItem('isAuthenticated', 'true');
                localStorage.setItem('userRole', userRole);
                localStorage.setItem('userId', userId);

                // Set the user object with session information and authentication status
                setUser({
                    username,
                    email,
                    sessionToken,
                    sessionId,
                    userId, // Add userId to the state
                });
                setIsAuthenticated(true);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An error occurred'); // Handle errors
            } finally {
                setLoading(false); // Set loading to false after request completes
            }
        };

        fetchUserData(); // Fetch user data on component mount

        // Auto logout logic
        const intervalId = setInterval(async () => {
            const storedSessionId = localStorage.getItem('sessionId');

            if (storedSessionId) {
                try {
                    const sessionResponse = await fetch(`http://192.168.40.129:8080/session/${storedSessionId}`);
                    if (!sessionResponse.ok) {
                        localStorage.removeItem('session');
                        localStorage.removeItem('sessionId');
                        localStorage.removeItem('userId');
                        localStorage.removeItem('userRole');
                        localStorage.removeItem('jwtToken');
                        localStorage.setItem('isAuthenticated', 'false');
                        setIsAuthenticated(false);
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
                        setUser(null); // Clear user state
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
                    setUser(null); // Clear user state on error
                }
            }
        }, 10000); // Check every 10 seconds

        return () => clearInterval(intervalId); // Clean up the interval on component unmount
    }, [ setIsAuthenticated ]);

    return { user, loading, error }; // Return user, loading, and error states
};

export default useFetchOAuth2User;
