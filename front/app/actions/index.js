import * as ACTION from '../_consts/actions';
import {API_SUCCESS_CODES, API_ERRORS} from '../_consts/api/api-codes';
import API from '../lib/api';
let api = new API();

/*USER*/

/* LOGIN */
function successAddTableReservation(data) {
    return {
        type: ACTION.ADD_TABLE_RESERVATION_SUCCESS,
        data
    }
}

export function addTableReservation(data) {
    return dispatch => {
        return api.createTableReservation(data).then((response) => response.data).then((data) => {
            console.log('add table reservation data: ', data);
            dispatch(successAddTableReservation(data.data))
        })
    }
}

function successAddLocalReservation(data) {
    return {
        type: ACTION.ADD_LOCAL_RESERVATION,
        data
    }
}

export function addLocalReservation(date) {
    return dispatch => {
        return api.createLocalReservation(date).then((response) => response.data).then((data) => {
            console.log('add local reservation data: ', data);
            dispatch(successAddLocalReservation(data.data))
        })
    }
}

function successTablesReservation(data) {
    return {
        type: ACTION.TABLES_RESERVATIONS_SUCCESS,
        data
    }
}

export function tablesReservation() {
    return dispatch => {
        return api.getTablesReservations().then((response) => response.data).then((data) => {
            console.log('tables reservations data: ', data);
            if (data.status < 0) {
                dispatch(successTablesReservation(null))
            } else {
                dispatch(successTablesReservation(data))
            }
        })
    }
}

function successLocalReservation(data) {
    return {
        type: ACTION.LOCAL_RESERVATIONS_SUCCESS,
        data
    }
}

export function localReservation() {
    return dispatch => {
        return api.getLocalReservations().then((response) => response.data).then((data) => {
            console.log('local reservations data: ', data);
            if (data.status < 0) {
                dispatch(successLocalReservation(null))
            } else {
                dispatch(successLocalReservation(data))
            }
        })
    }
}

function pendingLogin() {
    return {
        type: ACTION.LOGIN_PENDING
    }
}

function successLogin(user) {
    return {
        type: ACTION.LOGIN_SUCCESS,
        user
    }
}

function failLogin(errorText) {
    return {
        type: ACTION.LOGIN_FAILURE,
        errorText
    }
}

export function login(userData) {
    return dispatch => {
        dispatch(pendingLogin());

        return api.login(userData)
            .then(response => response.data)
            .then(data => {
                console.log('login data: ', data);
                let status = data.status;
                if (API_SUCCESS_CODES.includes(status)) {
                    dispatch(successLogin(data.user));
                } else {
                    let errorText = API_ERRORS[status];
                    dispatch(failLogin(errorText));
                }
            });
    }
}

/* REGISTRATION*/

function successRegister() {
    return {
        type: ACTION.REGISTRATION_SUCCESS
    }
}

function failRegister(errorText) {
    return {
        type: ACTION.REGISTRATION_FAILURE,
        errorText
    }
}

export function register(registerData) {
    return dispatch => {
        return api.register(registerData)
            .then(response => response.data)
            .then(data => {
                console.log('register data: ', data);
                let status = data.status;
                if (API_SUCCESS_CODES.includes(status)) {
                    dispatch(successRegister());
                } else {
                    let errorText = API_ERRORS[status];
                    dispatch(failRegister(errorText));
                }
            })
    }
}

/* LOGOUT */
function successLogout() {
    return {
        type: ACTION.LOGOUT_SUCCESS
    }
}

function failLogout() {
    return {
        type: ACTION.LOGIN_FAILURE
    }
}

export function logout() {
    return dispatch => {

        return api.logout()
            .then(response => response.data)
            .then(data => {
                console.log('logout data: ', data);
                if (API_SUCCESS_CODES.includes(data.status)) {
                    dispatch(successLogout());
                } else {
                    dispatch(failLogout());
                }
            })
    }
}

/* GET MENU */
function fetchMenu() {
    return {
        type: ACTION.MENU_PENDING
    }
}

function getMenuSuccess(menu) {
    return {
        type: ACTION.MENU_SUCCESS,
        menu
    }
}

export function getMenu() {
    return dispatch => {
        dispatch(fetchMenu());

        return api.getMenu()
            .then(response => response.data)
            .then(data => {
                console.log('get menu user: ', data);
                dispatch(getMenuSuccess(data));
            })
    }
}

/* Change user data*/
function pendingChange() {
    return {
        type: ACTION.UPDATE_USER_PENDING
    }
}

function successChange(user) {
    return {
        type: ACTION.UPDATE_USER_SUCCESS,
        user
    }
}

function failChange(errorText) {
    return {
        type: ACTION.UPDATE_USER_FAILURE,
        errorText
    }
}

export function changeUserData(id, userData) {
    return dispatch => {
        dispatch(pendingChange());

        return api.updateUser(id, userData)
            .then(response => response.data)
            .then(data => {
                console.log('user update: ', data);
                if (API_SUCCESS_CODES.includes(data.status)) {
                    dispatch(successChange(userData));
                } else {
                    dispatch(failChange());
                }
            });
    }
}

/* USER END*/

/* ADMIN */
function successGetAllUsers(users) {
    return {
        type: ACTION.GET_ALL_USERS_SUCCESS,
        users
    }
}

function failGetAllUsers() {
    return {
        type: ACTION.GET_ALL_USERS_FAILURE,
    }
}

export function getAllUsers() {
    return dispatch => {

        return api.getAllUsers_Admin()
            .then(response => response.data)
            .then(data => {
                console.log('get all users: ', data);
                dispatch(successGetAllUsers(data))
            });
    }
}

/* DELETE USER */

function successUserDelete(id) {
    return {
        type: ACTION.DELETE_USER_SUCCESS,
        id
    }
}

function failDeleteUser(errorText) {
    return {
        type: ACTION.DELETE_USER_FAILURE,
        errorText
    }
}

export function deleteUser(id) {
    return dispatch => {

        return api.deleteUser_Admin(id)
            .then(response => response.data)
            .then(data => {
                console.log('delete user: ', data);
                let status = data.status;
                if (API_SUCCESS_CODES.includes(status)) {
                    dispatch(successUserDelete(id));
                } else {
                    let errorText = API_ERRORS[status];
                    dispatch(failDeleteUser(errorText));
                }
            });
    }
}

/* GET USER BY ID */

function successGetUserById(user) {
    return {
        type: ACTION.GET_USER_BY_ID_SUCCESS,
        user
    }
}

function failGetUserById(errorText) {
    return {
        type: ACTION.GET_USER_BY_ID_FAILURE,
        errorText
    }
}

export function getUserById(id) {
    return dispatch => {

        return api.getUserByID_Admin(id)
            .then(response => response.data)
            .then(data => {
                console.log('get user by id: ', data);
                dispatch(successGetUserById(data))
            });
    }
}


function successUpdateUserAdmin(id, data) {
    return {
        type: ACTION.UPDATE_USER_ADMIN_SUCCESS,
        id,
        data
    }
}

function failUpdateUserAdmin(errorText) {
    return {
        type: ACTION.UPDATE_USER_ADMIN_FAILURE,
        errorText
    }
}

export function updateUserAdmin(id, userData) {
    return dispatch => {

        return api.updateUser_Admin(id, userData)
            .then(response => response.data)
            .then(data => {
                console.log('update user admin: ', data);
                dispatch(successUpdateUserAdmin(id, userData));
            });
    }
}

/* DELETE MEAL*/

function successDeleteMeal(id) {
    return {
        type: ACTION.DELETE_MEAL_SUCCESS,
        id
    }
}

function failDeleteMeal(errorText) {
    return {
        type: ACTION.DELETE_MEAL_FAILURE,
        errorText
    }
}

export function deleteMeal(id) {
    return dispatch => {

        return api.deleteMeal(id)
            .then(response => response.data)
            .then(data => {
                console.log('delete dish: ', data);
                let status = data.status;
                if (API_SUCCESS_CODES.includes(status)) {
                    dispatch(successDeleteMeal(id));
                }
            });
    }
}

/* MEAL UPDATE */
function successUpdateMeal(id, data) {
    return {
        type: ACTION.UPDATE_MEAL_SUCCESS,
        id,
        data
    }
}

function failUpdateMeal(errorText) {
    return {
        type: ACTION.UPDATE_MEAL_FAILURE,
        errorText
    }
}

export function updateMeal(id, mealData) {
    return dispatch => {

        return api.updateMeal(id, mealData)
            .then(response => response.data)
            .then(data => {
                console.log('update meal admin: ', data);
                dispatch(successUpdateMeal(id, data.data));
            });
    }
}

/* ADD UPDATE */
function successAddMeal(data) {
    return {
        type: ACTION.ADD_MEAL_SUCCESS,
        data
    }
}

function failAddMeal(errorText) {
    return {
        type: ACTION.ADD_MEAL_FAILURE,
        errorText
    }
}

export function addMeal(mealData) {
    return dispatch => {

        return api.addMeal(mealData)
            .then(response => response.data)
            .then(data => {
                console.log('add meal admin: ', data);
                dispatch(successAddMeal(data.data));
            });
    }
}

/* ADMIN END */

/* GET MEAL BY ID */

function successGetMealByID(data) {
    return {
        type: ACTION.GET_MEAL_BY_ID_SUCCESS,
        data
    }
}

export function getMealByID(id) {
    return dispatch => {

        return api.getMeal(id)
            .then(response => response.data)
            .then(data => {
                console.log('get meal data: ', data);
                dispatch(successGetMealByID(data));
            });
    }
}
/* GET ALL TABLES */

function successAllTables(data) {
    return {
        type: ACTION.GET_ALL_TABLES_SUCCESS,
        data
    }
}

export function allTables() {
    return dispatch => {

        return api.getTables().then(response => response.data)
            .then(data => {
                console.log('all tables data: ', data);
                dispatch(successAllTables(data))
            });
    }
}

/* DELETE TABLE */
function successDeleteTables(id) {
    return {
        type: ACTION.DELETE_TABLE_SUCCESS,
        id
    }
}

export function deleteTable(id) {
    return dispatch => {

        return api.deleteTable(id).then(response => response.data)
            .then(data => {
                console.log('delete table data: ', data);
                dispatch(successDeleteTables(id))
            });
    }
}

/* GET TABLE BY ID */
function successGetTableByID(data) {
    return {
        type: ACTION.GET_TABLE_BY_ID_SUCCESS,
        data
    }
}

export function getTableByID(id) {
    return dispatch => {

        return api.getTableByID(id).then(response => response.data)
            .then(data => {
                console.log('get table by id data: ', data);
                dispatch(successGetTableByID(data))
            });
    }
}

/* UPDATE TABLE */
function successUpdateTable(id, data) {
    return {
        type: ACTION.UPDATE_TABLE_SUCCESS,
        id,
        data
    }
}

export function updateTable(id, table) {
    return dispatch => {

        return api.updateTable(id, table).then(response => response.data)
            .then(data => {
                console.log('update table data: ', data);
                dispatch(successUpdateTable(id, table))
            });
    }
}

/* CREATE TABLE */
function successCreateTable(data) {
    return {
        type: ACTION.CREATE_TABLE_SUCCESS,
        data
    }
}

export function createTable(table) {
    return dispatch => {

        return api.createNewTable(table).then(response => response.data)
            .then(data => {
                console.log('create table data: ', data);
                dispatch(successCreateTable(table))
            });
    }
}

/* MANAGER */

/* TABLE FREQUENCY*/
function successTableFrequency(data) {
    return {
        type: ACTION.TABLE_FREQUENCY_SUCCESS,
        data
    }
}

export function tableFrequency(startDate, endDate) {
    return dispatch => {

        return api.getTableFrequency(startDate, endDate).then(response => response.data)
            .then(data => {
                console.log('table frequency: ', data);
                dispatch(successTableFrequency(data));
            });
    }
}

/* RESERVATION TRAFFIC */
function successReservationTraffic(data) {
    return {
        type: ACTION.RESERVATION_TRAFFIC_SUCCESS,
        data
    }
}

export function reservationTraffic(startDate, endDate) {
    return dispatch => {

        return api.getReservationTraffic(startDate, endDate).then(response => response.data)
            .then(data => {
                console.log('reservation traffic: ', data);
                dispatch(successReservationTraffic(data))
            });
    }
}

/* MEAL ORDERS */
function successMealOrders(data) {
    return {
        type: ACTION.MEAL_ORDERS_SUCCESS,
        data
    }
}

export function mealOrders(startDate, endDate) {
    return dispatch => {

        return api.getMealOrders(startDate, endDate).then(response => response.data)
            .then(data => {
                console.log('meal orders: ', data);
                dispatch(successMealOrders(data))
            });
    }
}

/* USER RESERVATIONS */
function successUsersReservations(data) {
    return {
        type: ACTION.USER_RESERVATIONS_SUCCESS,
        data
    }
}

export function usersReservations(startDate, endDate, topNumber) {
    return dispatch => {

        return api.getUserReservations(startDate, endDate, topNumber).then(response => response.data)
            .then(data => {
                console.log('user reservations: ', data);
                dispatch(successUsersReservations(data))
            });
    }
}