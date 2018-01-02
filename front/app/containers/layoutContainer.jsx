import React from 'react';
import Layout from './custom/layout';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom'
import {MAIN_NAV, ADMIN_NAV, MANAGER_NAV} from '../_consts/layouts/navigations';
import {MAIN_CONTENT, ADMIN_CONTENT, MANAGER_CONTENT} from '../_consts/layouts/contents';
import {getMenu, register, login, logout, changeUserData} from '../actions/index';

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
            ...adminLayoutProp
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
const mapStateToProps = state => {
    let store = state, props = null;
    let {user} = store;
    console.log('user :', user, Object.keys(user).length);
    if (Object.keys(user).length <= 1 || user.data.role === 'USER') {
        props = Object.assign(mainMapStateToProps(store), {current: 'main'});
    }
    else if (user.data.role === 'ADMIN'){
        props = Object.assign(adminMapStateToProps(store), {current: 'admin'});
    }
    else{
        props = null;
    }

    return Object.assign({}, sharedStateToProps(store), props)
};

const mergedProps = (stateProps, dispatchProps, ownProps) => {
    let {dispatch} = dispatchProps;
    let {user} = stateProps.shared, props = null;

    if (user.length === undefined || user.role === 'USER')
        props = mainMapDispatchToProps(dispatch);
    else if (user.role === 'ADMIN')
        props = null;
    else
        props = null;

    return Object.assign({}, stateProps, ownProps, sharedMapDispatchToProps(dispatch, ownProps), props)
};


export default withRouter(connect(mapStateToProps, null, mergedProps)(Layout));