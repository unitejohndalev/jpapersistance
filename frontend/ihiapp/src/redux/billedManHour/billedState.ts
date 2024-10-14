// src/redux/slices/billedManHoursSlice.ts
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { initialState } from './types';


const billedManHoursSlice = createSlice({
    name: 'billedManHours',
    initialState,
    reducers: {
        fetchBilledManHoursRequest: (state) => {
            state.isLoading = true;
            state.error = null;
        },
        fetchBilledManHoursSuccess: (state, action: PayloadAction<any[]>) => {
            state.billedMHInfo = action.payload;
            state.isLoading = false;
        },
        fetchBilledManHoursFailure: (state, action: PayloadAction<string>) => {
            state.isLoading = false;
            state.error = action.payload;
        },
    },
});

export const {
    fetchBilledManHoursRequest,
    fetchBilledManHoursSuccess,
    fetchBilledManHoursFailure,
} = billedManHoursSlice.actions;

export const billedManHoursReducer = billedManHoursSlice.reducer;
