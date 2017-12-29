import React from 'react';
import {BrowserRouter as Router} from 'react-router-dom';

/*  user imports    */
import MainLayout from './layouts/main';
import AdminLayout from './layouts/admin';
import ManagerLayout from './layouts/manager'
import API from './lib/api'
let api = new API();

const Routes = () => {
    api.getMenu();
    return (
        <Router>
            <MainLayout/>
            {/*<AdminLayout/>*/}
            {/*<ManagerLayout/>*/}
        </Router>
    );
};

export default Routes;