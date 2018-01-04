import {
    REGISTRATION_SUCCESS, REGISTRATION_FAILURE
} from '../_consts/actions';

const errors = (state = {}, action) => {
    switch (action.type) {
        case REGISTRATION_SUCCESS:
            return Object.assign({}, state, {registration: null});
        case REGISTRATION_FAILURE:
            return Object.assign({}, state, {registration: action.errorText});
        default:
            return state;
    }
};


const registration = (state = {}, action) => {
    switch (action.type) {
        case REGISTRATION_SUCCESS:
            return Object.assign({}, state, {success: true});
        case REGISTRATION_FAILURE:
            return Object.assign({}, state, {success: false});
        default:
            return state;
    }
};

const main = (state = {}, action) => {
    return {
        errors: errors(state.errors, action),
        registration: registration(state.registration, action)
    }
};

export default main;