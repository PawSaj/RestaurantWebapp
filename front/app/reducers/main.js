import {
    REGISTRATION_SUCCESS,
    REGISTRATION_FAILURE,
    LOCAL_RESERVATIONS_SUCCESS,
    ADD_LOCAL_RESERVATION,
    TABLES_RESERVATIONS_SUCCESS, ADD_TABLE_RESERVATION_SUCCESS
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
            let newStateArray = state.local;
            newStateArray.push(action.data);
            return Object.assign({}, state, {local: newStateArray});
        case ADD_TABLE_RESERVATION_SUCCESS:
            let newTableStateArray = state.local;
            newTableStateArray.push(action.data);
            return Object.assign({}, state, {tables: newTableStateArray});
        case TABLES_RESERVATIONS_SUCCESS:
            return Object.assign({}, state, {tables: action.data});
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
