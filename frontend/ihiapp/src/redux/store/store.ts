import createSagaMiddleware from "redux-saga";
import { configureStore } from "@reduxjs/toolkit";
import rootSaga from "../rootSaga";
import { AuthInputReducer, authReducer } from "../login/authState";
import { billedManHoursReducer } from "../billedManHour/billedState";



const saga = createSagaMiddleware();
export const store = configureStore({
  reducer: {

    // AUTH REDUCER
    authReducer: authReducer,
    AuthInputReducer: AuthInputReducer,

    //Bille Man Hour REDUCER
    billedManHoursReducer: billedManHoursReducer
  
    // add more reducers here
    

  },
  middleware: [saga],
});

saga.run(rootSaga);

export type RootState = ReturnType<typeof store.getState>;
export default store;
