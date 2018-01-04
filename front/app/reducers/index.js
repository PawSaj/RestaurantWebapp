import {combineReducers} from 'redux';
import main from './main';
import admin from './admin';
import {
    MENU_SUCCESS,
    MENU_PENDING,
    LOGIN_PENDING,
    LOGIN_FAILURE,
    LOGIN_SUCCESS,
    LOGOUT_SUCCESS,
    UPDATE_USER_SUCCESS,
    UPDATE_USER_FAILURE
} from '../_consts/actions';

const errors = (state = {}, action) => {
    switch (action.type) {
        case LOGIN_SUCCESS:
            return Object.assign({}, state, {login: null});
        case LOGIN_FAILURE:
            return Object.assign({}, state, {login: action.errorText});
        default:
            return state;
    }
};


const menu = (state = {}, action) => {
    switch (action.type) {
        case MENU_PENDING:
            return Object.assign({}, state, {pending: true});
        case MENU_SUCCESS:
            return Object.assign({}, state, {pending: false, data: action.menu});
        default:
            return state;
    }
};

const user = (state = {}, action) => {
    switch (action.type) {
        case LOGOUT_SUCCESS:
            window.localStorage.removeItem('currentUser');
            return {};
        case LOGIN_PENDING:
            return Object.assign({}, state, {pending: true});
        case LOGIN_SUCCESS:
            window.localStorage.setItem('currentUser', JSON.stringify(action.user));
            return Object.assign({}, state, {pending: false, data: action.user});
        case LOGIN_FAILURE:
            return Object.assign({}, state, {pending: false});
        case UPDATE_USER_SUCCESS:
            return Object.assign({}, state, {pending: false, data: action.user});
        case UPDATE_USER_FAILURE:
            return Object.assign({}, state);
        default:
            return state;
    }
};

export default combineReducers({errors, user, menu, main, admin});