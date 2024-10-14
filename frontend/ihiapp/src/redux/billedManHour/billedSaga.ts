// src/redux/sagas/billedManHoursSaga.ts
import { call, put, takeLatest } from 'redux-saga/effects';
import { fetchBilledManHoursRequest, fetchBilledManHoursSuccess, fetchBilledManHoursFailure } from './billedState';
import { getBilledManhours } from '../../api/Api';
import axios from 'axios';

function* fetchBilledManHoursSaga() {
    try {
        // Use axios for the API call
        const response = yield call(axios.get, getBilledManhours);
        // axios automatically parses JSON, so you don't need to call response.json()
        yield put(fetchBilledManHoursSuccess(response.data));
    } catch (error) {
        // Handle the error appropriately
        yield put(fetchBilledManHoursFailure((error as Error).message));
    }
}

export function* watchFetchBilledManHours() {
    yield takeLatest(fetchBilledManHoursRequest.type, fetchBilledManHoursSaga);
}
