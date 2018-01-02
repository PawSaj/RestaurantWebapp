import React from 'react';
import {BrowserRouter as Router} from 'react-router-dom';
import {Provider} from 'react-redux';

/*  user imports    */
import LayoutContainer from './containers/layoutContainer';

const Routes = ({store}) => {

    return (
        <Provider store={store}>
            <Router>
                <LayoutContainer/>
            </Router>
        </Provider>
    );
};

export default Routes;