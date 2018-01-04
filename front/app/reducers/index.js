import {combineReducers} from 'redux';
import main from './main';
import admin from './admin';
import manager from './manager';
import {
    MENU_SUCCESS,
    MENU_PENDING,
    LOGIN_PENDING,
    LOGIN_FAILURE,
    LOGIN_SUCCESS,
    LOGOUT_SUCCESS,
    UPDATE_USER_SUCCESS,
    UPDATE_USER_FAILURE,
    DELETE_MEAL_FAILURE,
    DELETE_MEAL_SUCCESS,
    UPDATE_MEAL_SUCCESS,
    UPDATE_MEAL_FAILURE,
    ADD_MEAL_SUCCESS,
    ADD_MEAL_FAILURE, GET_MEAL_BY_ID_SUCCESS
} from '../_consts/actions';
import {
    stateAfterMenuDelete,
    stateAfterMenuAdd,
    stateAfterMenuUpdate,
    stateAfterMealGetByID
} from '../lib/helpers/stateHelpers';

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
    let {data} = state;
    switch (action.type) {
        case MENU_PENDING:
            return Object.assign({}, state, {pending: true});
        case MENU_SUCCESS:
            return Object.assign({}, state, {pending: false, data: action.menu, all: true});
        case DELETE_MEAL_SUCCESS:
            return Object.assign({}, state, {data: stateAfterMenuDelete(data, action)});
        case UPDATE_MEAL_SUCCESS:
            return Object.assign({}, state, {data: stateAfterMenuUpdate(data, action)});
        case ADD_MEAL_SUCCESS:
            return Object.assign({}, state, {data: stateAfterMenuAdd(data, action)});
        case GET_MEAL_BY_ID_SUCCESS:
            return Object.assign({}, state, {data: stateAfterMealGetByID(action)});
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

export default combineReducers({errors, user, menu, main, admin, manager});