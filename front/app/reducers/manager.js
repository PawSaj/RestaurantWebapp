import {MEAL_ORDERS_SUCCESS, RESERVATION_TRAFFIC_SUCCESS} from '../_consts/actions';

const orders = (state = {}, action) => {
    switch (action.type) {
        case MEAL_ORDERS_SUCCESS:
            return Object.assign({}, state, {data: action.data});
        default:
            return state;
    }
};

const reservations = (state = {}, action) => {
    switch (action.type) {
        default:
            return state;
    }
};


const traffic = (state = {}, action) => {
    switch (action.type) {
        case RESERVATION_TRAFFIC_SUCCESS:
            return Object.assign({}, state, {data: action.data});
        default:
            return state;
    }
};


const frequency = (state = {}, action) => {
    switch (action.type) {
        default:
            return state;
    }
};

const manager = (state = {}, action) => {
    return {
        reservations: reservations(state.reservations, action),
        orders: orders(state.orders, action),
        traffic: traffic(state.traffic, action),
        frequency: frequency(state.orders, action)
    }
};

export default manager;
