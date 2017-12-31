import React from 'react';
import Navigation from './_navigation';
import {Switch, Redirect, Route} from 'react-router-dom';
import Menu from '../../components/main/menu';
import Content from './_content';

const setLoggedIn = (user) =>{
    for (let key in user) {
        if (user.hasOwnProperty(key)) {
            return true;
        }
    }
    return false;
};

// {navigation, content}
const Layout = (props) => {
    let {navigation, user, logoutUser} = props;
    let loggedIn = setLoggedIn(user);

    return (
        <div>
            <Navigation navigation={navigation} loggedIn={loggedIn} logoutUser={logoutUser}/>
            <Content {...props} loggedIn={loggedIn}/>
        </div>
    );
};

export default Layout;