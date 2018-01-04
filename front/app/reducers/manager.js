const orders = (state = {}, action) => {
    switch (action.type) {
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
