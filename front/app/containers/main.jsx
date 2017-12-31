import React from 'react';
import Layout from './custom/layout';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom'
import {MAIN_NAV} from '../_consts/layouts/navigations';
import {MAIN_CONTENT} from '../_consts/layouts/contents';
import {getMenu, login, logout, changeUserData} from '../actions/index';

let layoutProp = {
    navigation: MAIN_NAV,
    content: MAIN_CONTENT
};

const mapStateToProps = state => {
    let store = state.main;
    return {
        user: store.user,
        menu: store.menu,
        ...layoutProp
    }
};

const mapDispatchToProps = dispatch => {
    return {
        getMenu: () => {
            dispatch(getMenu());
        },
        loginUser: (userData) => {
            dispatch(login(userData));
        },
        logoutUser: () => {
            dispatch(logout());
        },
        changeUserData: (id, userData) => {
            dispatch(changeUserData(id, userData));
        }
    }
};


export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Layout));