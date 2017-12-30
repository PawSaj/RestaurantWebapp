import React from 'react';
import Navigation from './_navigation';
import {Switch, Redirect, Route} from 'react-router-dom';
import Menu from '../../components/main/menu';
import Content from './_content';


// {navigation, content}
const Layout = (props) => {
    let {navigation, user} = props;
    let loggedIn = false;
    if (user && user.length > 0) {
        loggedIn = true;
    }

    return (
        <div>
            <Navigation navigation={navigation} loggedIn={loggedIn}/>
            <Content {...props} loggedIn={loggedIn}/>
        </div>
    );
};

export default Layout;