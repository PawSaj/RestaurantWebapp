import * as ACTION from '../_consts/actions';
import {API_SUCCESS_CODES, API_ERRORS} from '../_consts/api/api-codes';
import API from '../lib/api';
let api = new API();


/* LOGIN */
function pendingLogin() {
    return {
        type: ACTION.LOGIN_PENDING
    }
}

function successLogin(user) {
    return {
        type: ACTION.LOGIN_SUCCESS,
        user
    }
}

function failLogin(errorText) {
    return {
        type: ACTION.LOGIN_FAILURE,
        errorText
    }
}

export function login(userData) {
    return dispatch => {
        dispatch(pendingLogin());

        return api.login(userData)
            .then(response => response.data)
            .then(data => {
                if (data.status !== undefined) {
                    let errorText = API_ERRORS[data.status];
                    dispatch(failLogin(errorText));
                } else {
                    dispatch(successLogin(data));
                }
            });
    }
}

/* REGISTRATION*/

function successRegister() {
    return {
        type: ACTION.REGISTRATION_SUCCESS
    }
}

function failRegister(errorText) {
    return {
        type: ACTION.REGISTRATION_FAILURE,
        errorText
    }
}

export function register(registerData) {
    return dispatch => {
        dispatch(sendRequest());

        return api.register(registerData)
            .then(response => response.data)
            .then(data => {
                let status = data.status;
                if (API_SUCCESS_CODES.includes(status)) {
                    dispatch(successRegister());
                } else {
                    let errorText = API_ERRORS[status];
                    dispatch(failRegister(errorText));
                }
            })
    }
}

/* LOGOUT */
function successLogout() {
    return {
        type: ACTION.LOGOUT_SUCCESS
    }
}

function failLogout() {
    return {
        type: ACTION.LOGIN_FAILURE
    }
}

export function logout() {
    return dispatch => {
        dispatch(sendRequest());

        return api.logout()
            .then(response => response.data)
            .then(data => {
                if (API_SUCCESS_CODES.includes(data.status)) {
                    dispatch(successLogout());
                } else {
                    dispatch(failLogout());
                }
            })
    }
}

/* GET MENU */
function fetchMenu() {
    return {
        type: ACTION.MENU_PENDING
    }
}

function getMenuSuccess(menu) {
    return {
        type: ACTION.MENU_SUCCESS,
        menu
    }
}

export function getMenu() {
    return dispatch => {
        dispatch(fetchMenu());

        return api.getMenu()
            .then(response => response.data)
            .then(data => {
                dispatch(getMenuSuccess(data));
            })
    }
}
