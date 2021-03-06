export const BASE_URL = 'http://localhost:8080';

export const CONTENT_TYPES = {
    JSON: 'application/json',
    URLENCODED: 'application/x-www-form-urlencoded'
};

export const IMAGE_ENDPOINTS = {
    SEND: '/sendImage',
    GET: '/ getImage'
};

export const MAIN_ENDPOINTS = {
    MENU: '/menu',
    RESTAURANT_RESERVATION: '/restaurantReservation',
    TABLE_RESERVATION: '/tableReservation'
};

export const USER_ENDPOINTS = {
    LOGIN: '/login',
    LOGOUT: '/logout',
    REGISTRATION: '/registration',
    USERS: '/user'
};

export const ADMIN_ENDPOINTS = {
    USERS: '/admin/users',
    MEALS: '/admin/meal',
    TABLES: '/admin/tables'
};

export const MANAGER_ENDPOINTS = {
    TABLE: '/management/reservation/table/frequency',
    TRAFFIC: '/management/reservation/traffic',
    ORDER: '/management/meal/order',
    RESERVATION:'/management/user/reservation'
};