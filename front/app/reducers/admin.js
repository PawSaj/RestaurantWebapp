import {
    GET_ALL_USERS_FAILURE, GET_ALL_USERS_SUCCESS, DELETE_USER_SUCCESS, DELETE_USER_FAILURE
} from '../_consts/actions';

const stateAfterDelete = (data, action) => {
    let elementIndex = null;
    data.map((user, index) => {
        if (user.id === action.id) {
            elementIndex =  index;
        }
    });
    return data.slice(0, elementIndex).concat(data.slice(elementIndex + 1, data.lenght));
};

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
            return Object.assign({}, state, {data: action.users});
        case DELETE_USER_SUCCESS:
            return Object.assign({}, state, {data: stateAfterDelete(data, action)});
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
