import {
    GET_ALL_USERS_FAILURE, GET_ALL_USERS_SUCCESS
} from '../_consts/actions';

const errors = (state = {}, action) => {
    switch (action.type) {
        case GET_ALL_USERS_SUCCESS:
            return Object.assign({}, state, {users: null});
        case GET_ALL_USERS_FAILURE:
            return Object.assign({}, state, {users: action.errorText});
        default:
            return state;
    }
};

const users = (state = {}, action) => {
    switch (action.type) {
        case GET_ALL_USERS_SUCCESS:
            return Object.assign({}, state, {data: action.users});
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
