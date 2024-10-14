// src/sagas/authSaga.ts
import { call, put, takeLatest, delay } from 'redux-saga/effects';
import axios from 'axios';
import { loginSuccess, loginFailure, loginFetch, autoLogout } from './authState';
import { postAuthenticate } from '../../api/Api';
import { jwtDecode } from 'jwt-decode';
import { role } from '../../types/Types';
function* handleLogin(action: any) {
    try {
        const { username, password } = action.payload;
        const response = yield call(axios.post, postAuthenticate, { username, password });

        if (response.status === 200) {
            const data = response.data;
            const sessionIdFromBackend = data.additionalDetails.sessionId;
            const sessionToken = data.additionalDetails.sessionToken; // Extract session token
            const jwtToken = data.token;
            const userIdFromBackend = data.additionalDetails.userId; // Extract user ID

            const decodedToken: role = jwtDecode(jwtToken);
            const userRole = decodedToken.role;

            // Dispatch loginSuccess with all relevant data
            yield put(loginSuccess({
                token: jwtToken,
                sessionId: sessionIdFromBackend,
                userId: userIdFromBackend,
                sessionToken,
                userRole
            }));
        } else {
            const errorMessage = response.data.message;
            yield put(loginFailure(errorMessage));
        }
    } catch (error) {
        yield put(loginFailure(error instanceof Error ? error.message : String(error)));
    }
}


function* checkSession() {
    while (true) {
        const storedSessionId = localStorage.getItem('sessionId');
        const storedUserId = localStorage.getItem('userId');

        if (storedSessionId && storedUserId) {
            try {
                const sessionResponse = yield call(fetch, `http://192.168.40.129:8080/session/${storedSessionId}`);
                if (!sessionResponse.ok) {
                    yield put(autoLogout()); // Trigger auto logout
                    return; // Exit early
                }

                const sessionData = yield sessionResponse.json();

                // Check if the session is invalid
                if (sessionData.status === false) {
                    yield put(autoLogout()); // Trigger auto logout
                }
            } catch (error) {
                yield put(autoLogout()); // Trigger auto logout on error
            }
        }
        yield delay(10000); // Check every 10 seconds
    }
}

export function* authSaga() {
    yield takeLatest(loginFetch.type, handleLogin);
    yield call(checkSession); // Start the session checking saga
}
