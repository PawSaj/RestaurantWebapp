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
                console.log('login data: ', data);
                let status = data.status;
                if (API_SUCCESS_CODES.includes(status)) {
                    dispatch(successLogin(data.user));
                } else {
                    let errorText = API_ERRORS[status];
                    dispatch(failLogin(errorText));
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
        return api.register(registerData)
            .then(response => response.data)
            .then(data => {
                console.log('register data: ', data);
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

        return api.logout()
            .then(response => response.data)
            .then(data => {
                console.log('logout data: ', data);
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
                console.log('get menu user: ', data);
                dispatch(getMenuSuccess(data));
            })
    }
}

/* Change user data*/
function pendingChange() {
    return {
        type: ACTION.UPDATE_USER_PENDING
    }
}

function successChange(user) {
    return {
        type: ACTION.UPDATE_USER_SUCCESS,
        user
    }
}

function failChange(errorText) {
    return {
        type: ACTION.UPDATE_USER_FAILURE,
        errorText
    }
}

export function changeUserData(id, userData) {
    return dispatch => {
        dispatch(pendingChange());

        return api.updateUser(id, userData)
            .then(response => response.data)
            .then(data => {
                console.log('user update: ', data);
                if (API_SUCCESS_CODES.includes(data.status)) {
                    dispatch(successChange(userData));
                } else {
                    dispatch(failChange());
                }
            });
    }
}