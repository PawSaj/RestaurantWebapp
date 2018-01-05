import {
    REGISTRATION_SUCCESS, REGISTRATION_FAILURE, LOCAL_RESERVATIONS_SUCCESS,ADD_LOCAL_RESERVATION
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

const reservations = (state = {}, action) => {
    switch (action.type) {
        case LOCAL_RESERVATIONS_SUCCESS:
            return Object.assign({}, state, {local: action.data});
        case ADD_LOCAL_RESERVATION:
            return Object.assign({}, state);
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
        registration: registration(state.registration, action),
        reservations: reservations(state.reservations, action)
    }
};

export default main;
