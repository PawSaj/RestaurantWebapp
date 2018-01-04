import {
    GET_ALL_USERS_FAILURE,
    GET_ALL_USERS_SUCCESS,
    DELETE_USER_SUCCESS,
    DELETE_USER_FAILURE,
    GET_USER_BY_ID_SUCCESS,
    GET_USER_BY_ID_FAILURE,
    UPDATE_USER_ADMIN_SUCCESS,
    UPDATE_USER_FAILURE
} from '../_consts/actions';
import {stateAfterDelete,stateAfterUpdate} from '../lib/helpers/stateHelpers';

const errors = (state = {}, action) => {
    switch (action.type) {
        case GET_ALL_USERS_SUCCESS:
            return Object.assign({}, state, {users: null});
        case GET_ALL_USERS_FAILURE:
            return Object.assign({}, state, {users: action.errorText});
        case DELETE_USER_SUCCESS:
            return Object.assign({}, state, {user: null});
        case DELETE_USER_FAILURE:
            return Object.assign({}, state, {user: action.errorText});
        default:
            return state;
    }
};

const users = (state = {}, action) => {
    let {data} = state;
    switch (action.type) {
        case GET_ALL_USERS_SUCCESS:
            return Object.assign({}, state, {data: action.users, all: true});
        case DELETE_USER_SUCCESS:
            return Object.assign({}, state, {data: stateAfterDelete(data, action)});
        case UPDATE_USER_ADMIN_SUCCESS:
            return Object.assign({}, state, {data: stateAfterUpdate(data, action)});
        case GET_USER_BY_ID_SUCCESS:
            return Object.assign({}, state, {data: [action.user]});
        default:
            return state;
    }
};


const admin = (state = {}, action) => {
    return {
        errors: errors(state.errors, action),
        users: users(state.users, action)
    }
};

export default admin;
