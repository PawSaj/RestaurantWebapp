import React from 'react';
import {BrowserRouter as Router} from 'react-router-dom';
import {Provider} from 'react-redux';

/*  user imports    */
import MainLayout from './containers/main';
import AdminLayout from './containers/admin';
import ManagerLayout from './containers/manager'

const Routes = ({store}) => {
    let element = null, user = store.getState().user;

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