// src/state/authSlice.ts
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { initialState, UserInput } from './types';

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        loginFetch: (state, action: PayloadAction<{ username: string; password: string }>) => {
            state.error = null;
        },
        loginSuccess: (state, action: PayloadAction<{ token: string; sessionId: string; userRole: string; userId: string; sessionToken: string }>) => {
            state.isAuthenticated = true;
            state.sessionId = action.payload.sessionId;
            state.token = action.payload.token;
            state.userRole = action.payload.userRole;
            state.userId = action.payload.userId; // Store userId
            state.sessionToken = action.payload.sessionToken; // Store sessionToken

            // Store in localStorage
            localStorage.setItem('isAuthenticated', 'true');
            localStorage.setItem('sessionId', action.payload.sessionId);
            localStorage.setItem('jwtToken', action.payload.token);
            localStorage.setItem('userRole', action.payload.userRole);
            localStorage.setItem('userId', action.payload.userId); // Store userId
            localStorage.setItem('session', action.payload.sessionToken); // Store sessionToken
        },
        loginFailure: (state, action: PayloadAction<string>) => {
            state.isAuthenticated = false;
            state.error = action.payload;
        },
        logout: (state) => {
            state.isAuthenticated = false;
            state.sessionId = null;
            state.token = null;
            state.userRole = null;
            state.error = null;

            // Clear localStorage
            localStorage.removeItem('session');
            localStorage.removeItem('sessionId');
            localStorage.removeItem('userId');
            localStorage.removeItem('userRole');
            localStorage.removeItem('jwtToken');
            localStorage.setItem('isAuthenticated', 'false');
        },
        autoLogout: (state) => {
            // Handle auto logout logic
            state.isAuthenticated = false;
            state.sessionId = null;
            state.token = null;
            state.userRole = null;
            state.userId = null; // Reset userId
            state.sessionToken = null; // Reset sessionToken
            state.error = null;

            // Clear localStorage
            localStorage.removeItem('session');
            localStorage.removeItem('sessionId');
            localStorage.removeItem('userId');
            localStorage.removeItem('userRole');
            localStorage.removeItem('jwtToken');
            localStorage.setItem('isAuthenticated', 'false');
        },

    },
});

// Export actions and reducer
export const { loginFetch, loginSuccess, loginFailure, logout, autoLogout } = authSlice.actions;
export const authReducer = authSlice.reducer;

// Initial state for user input
const inputInitialState: UserInput = {
    username: "",
    password: "",
};

// Create slice for user input state
const InputSlice = createSlice({
    name: "userInput",
    initialState: inputInitialState,
    reducers: {
        setUserField: (state, action: PayloadAction<Partial<UserInput>>) => {
            return { ...state, ...action.payload };
        },
    },
});

// Export input actions and reducer
export const { setUserField } = InputSlice.actions;
export const AuthInputReducer = InputSlice.reducer;
