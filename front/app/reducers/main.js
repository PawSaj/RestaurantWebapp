import {
    MENU_SUCCESS, MENU_PENDING
} from '../_consts/actions';

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
        default:
            return state;
    }
};

const main = (state = {}, action) => {
    return {
        user: user(state.user, action),
        menu: menu(state.menu, action)
    }
};

export default main;
