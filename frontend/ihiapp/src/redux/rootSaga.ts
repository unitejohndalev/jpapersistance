// rootSaga.ts
import { all } from "redux-saga/effects";
import { authSaga } from "./login/authSaga";
import { watchFetchBilledManHours } from "./billedManHour/billedSaga";



export default function* rootSaga() {
  yield all([
    // Auth Saga
    authSaga(),

    //BILLED MAN HOUR SAGA
    watchFetchBilledManHours()

    //add saga here
  ]);
}
