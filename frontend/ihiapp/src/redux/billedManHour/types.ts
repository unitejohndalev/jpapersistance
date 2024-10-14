export interface BilledManHoursState {
    billedMHInfo: any[]; // Update with the correct type
    isLoading: boolean;
    error: string | null;
}

export const initialState: BilledManHoursState = {
    billedMHInfo: [],
    isLoading: false,
    error: null,
};
