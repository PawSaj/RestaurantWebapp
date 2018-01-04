import React from 'react';
import Layout from './custom/layout';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom'
import {MAIN_NAV, ADMIN_NAV, MANAGER_NAV} from '../_consts/layouts/navigations';
import {MAIN_CONTENT, ADMIN_CONTENT, MANAGER_CONTENT} from '../_consts/layouts/contents';
import {
    getMenu,
    register,
    login,
    logout,
    changeUserData,
    getAllUsers,
    deleteUser,
    getUserById,
    updateUserAdmin,
    deleteMeal,
    updateMeal,
    addMeal,
    getMealByID,
    allTables,
    deleteTable,
    getTableByID,
    updateTable,
    createTable, usersReservations, mealOrders, reservationTraffic, tableFrequency
} from '../actions/index';

const mainLayoutProp = {
    navigation: MAIN_NAV,
    content: MAIN_CONTENT
};

const adminLayoutProp = {
    navigation: ADMIN_NAV,
    content: ADMIN_CONTENT
};

let managerLayoutProp = {
    navigation: MANAGER_NAV,
    content: MANAGER_CONTENT
};

const sharedStateToProps = (store) => {
    return {
        shared: {
            user: store.user,
            menu: store.menu,
            errors: store.errors
        }
    }
};

const mainMapStateToProps = (store) => {
    return {
        main: {
            errors: store.main.errors,
            registration: store.main.registration,
            ...mainLayoutProp
        }
    }
};

const adminMapStateToProps = (store) => {
    return {
        admin: {
            errors: store.admin.errors,
            users: store.admin.users,
            tables: store.admin.tables,
            ...adminLayoutProp
        }
    }
};

const managerMapStateToProps = (store) => {
    return {
        manager: {
            frequency: store.manager.frequency,
            orders: store.manager.orders,
            reservations: store.manager.reservations,
            traffic:store.manager.traffic,
            ...managerLayoutProp
        }
    }
};

const sharedMapDispatchToProps = (dispatch, ownProps) => {
    return {
        sharedFunctions: {
            getMenu: () => {
                dispatch(getMenu());
            },
            loginUser: (userData) => {
                dispatch(login(userData));
            },
            logoutUser: () => {
                dispatch(logout()).then(() => ownProps.history.push('/'));
            },
            changeUserData: (id, userData) => {
                dispatch(changeUserData(id, userData));
            }
        }
    }
};

const mainMapDispatchToProps = (dispatch) => {
    return {
        mainFunctions: {
            registerUser: (userData) => {
                dispatch(register(userData));
            }
        }
    }
};

const adminMapDispatchToProps = (dispatch, ownProps) => {
    return {
        adminFunctions: {
            getAllUsers: () => {
                dispatch(getAllUsers());
            },
            deleteUser: (id) => {
                dispatch(deleteUser(id));
            },
            getUserById: (id) => {
                dispatch(getUserById(id));
            },
            updateUserAdmin: (id, userData) => {
                dispatch(updateUserAdmin(id, userData)).then(() => ownProps.history.push('/users'));
            },
            deleteMeal: (id) => {
                dispatch(deleteMeal(id));
            },
            updateMeal: (id, mealData) => {
                dispatch(updateMeal(id, mealData)).then(() => ownProps.history.push('/menu'));
            },
            addMeal: (mealData) => {
                dispatch(addMeal(mealData));
            },
            getMealByID: (id) => {
                dispatch(getMealByID(id));
            },
            getTables: () => {
                dispatch(allTables());
            },
            deleteTable: (id) => {
                dispatch(deleteTable(id));
            },
            getTableByID: (id) => {
                dispatch(getTableByID(id));
            },
            updateTable: (id, table) => {
                dispatch(updateTable(id, table)).then(() => ownProps.history.push('/tables'));
            },
            createTable: (table) => {
                dispatch(createTable(table));
            }
        }
    }
};

const managerMapDispatchToProps = (dispatch) => {
    return {
        managerFunctions: {
            usersReservations: (startDate, endDate, topNumber) => {
                dispatch(usersReservations(startDate, endDate, topNumber));
            },
            mealOrders: (startDate, endDate) => {
                dispatch(mealOrders(startDate, endDate));
            },
            reservationTraffic: (startDate, endDate) => {
                dispatch(reservationTraffic(startDate, endDate))
            },
            tableFrequency: (startDate, endDate) => {
                dispatch(tableFrequency(startDate, endDate));
            }
        }
    }
};

const mapStateToProps = state => {
    let store = state, props = null;
    let {user} = store;
    if (Object.keys(user).length <= 1 || user.data.role === 'ROLE_USER') {
        props = Object.assign(mainMapStateToProps(store), {current: 'main'});
    }
    else if (user.data.role === 'ROLE_ADMIN') {
        props = Object.assign(adminMapStateToProps(store), {current: 'admin'});
    }
    else {
        props = Object.assign(managerMapStateToProps(store), {current: 'manager'});
    }

    return Object.assign({}, sharedStateToProps(store), props)
};

const mergedProps = (stateProps, dispatchProps, ownProps) => {
    let {dispatch} = dispatchProps;
    let {user} = stateProps.shared, props = null;

    if (Object.keys(user).length <= 1 || user.data.role === 'ROLE_USER') {
        props = mainMapDispatchToProps(dispatch);
    }
    else if (user.data.role === 'ROLE_ADMIN') {
        props = adminMapDispatchToProps(dispatch, ownProps);
    }
    else {
        props = managerMapDispatchToProps(dispatch);
    }

    return Object.assign({}, stateProps, ownProps, sharedMapDispatchToProps(dispatch, ownProps), props)
};


export default withRouter(connect(mapStateToProps, null, mergedProps)(Layout));