import React from 'react';
import {BrowserRouter as Router} from 'react-router-dom';
import {Provider} from 'react-redux';

/*  user imports    */
import MainLayout from './containers/main';
import AdminLayout from './layouts/admin';
import ManagerLayout from './layouts/manager'

const Routes = ({store}) => {
    let element = null, user = store.getState().main.user;

    if (user.length === undefined || user.role === 'USER') {
        element = <MainLayout/>;
    } else if (user.role === 'ADMIN') {
        element = <AdminLayout/>
    } else if (user.role === 'MANAGER') {
        element = <ManagerLayout/>
    }

    return (
        <Provider store={store}>
            <Router>
                {element}
            </Router>
        </Provider>
    );
};

export default Routes;