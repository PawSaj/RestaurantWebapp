import React from 'react';
import Layout from './custom/layout';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom'
import {MAIN_NAV} from '../_consts/layouts/navigations';
import {MAIN_CONTENT} from '../_consts/layouts/contents';
import {getMenu, register, login, logout, changeUserData} from '../actions/index';

let layoutProp = {
    navigation: MAIN_NAV,
    content: MAIN_CONTENT
};

const mapStateToProps = state => {
    let store = state;
    return {
        shared: {
            user: store.user,
            menu: store.menu,
            errors: store.errors
        },
        main: {
            errors: store.main.errors,
            registration: store.main.registration,
            ...layoutProp
        }
    }
};

const mapDispatchToProps = (dispatch, ownProps) => {
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
        },
        mainFunctions: {
            registerUser: (userData) => {
                dispatch(register(userData));
            }
        }
    }
};


export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Layout));