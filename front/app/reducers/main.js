import {
    MENU_SUCCESS, MENU_PENDING, LOGIN_PENDING, LOGIN_FAILURE, LOGIN_SUCCESS, LOGOUT_SUCCESS
} from '../_consts/actions';

const errors = (state = {}, action) => {
    switch (action.type) {
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
            return Object.assign({}, state, {pending: false, menu: action.menu});
        default:
            return state;
    }
};

const user = (state = {}, action) => {
    switch (action.type) {
        case LOGOUT_SUCCESS:
            return {};
        case LOGIN_PENDING:
            return Object.assign({}, state, {pending: true});
        case LOGIN_SUCCESS:
            return Object.assign({}, state, {pending: false, data: action.user});
        default:
            return state;
    }
};

const main = (state = {}, action) => {
    return {
        user: user(state.user, action),
        menu: menu(state.menu, action),
        errors: errors(state.errors, action)
    }
};

export default main;
