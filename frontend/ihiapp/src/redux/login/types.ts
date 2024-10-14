
export interface AuthState {
    isAuthenticated: boolean;
    sessionId: string | null;
    userId: string | null; // Added userId
    sessionToken: string | null; // Added sessionToken
    error: string | null;
    token: string | null;
    userRole: string | null;
}

export const initialState: AuthState = {
    isAuthenticated: localStorage.getItem('isAuthenticated') === 'true',
    sessionId: localStorage.getItem('sessionId') || null,
    userId: localStorage.getItem('userId') || null, // Initialize userId
    sessionToken: localStorage.getItem('session') || null, // Initialize sessionToken
    error: null,
    token: localStorage.getItem('jwtToken') || null,
    userRole: localStorage.getItem('userRole') || null, // Initialize userRole
};

export type UserInput = {
    username: string;
    password: string;
};
