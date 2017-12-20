import React from 'react';
import {BrowserRouter as Router} from 'react-router-dom';

/*  user imports    */
import UserLayout from './layouts/user';
import AdminLayout from './layouts/admin';
import ManagerLayout from './layouts/manager'

const Routes = () => {
    return (
        <Router>
           <UserLayout/>
        </Router>
    );
};

export default Routes;