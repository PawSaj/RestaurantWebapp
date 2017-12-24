import React from 'react';
import {BrowserRouter as Router} from 'react-router-dom';

/*  user imports    */
import MainLayout from './layouts/main';
import AdminLayout from './layouts/admin';
import ManagerLayout from './layouts/manager'

const Routes = () => {
    return (
        <Router>
            {/*<MainLayout/>*/}
            <AdminLayout/>
        </Router>
    );
};

export default Routes;