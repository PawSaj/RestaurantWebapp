import React from 'react';
import {Provider} from 'react-redux';
import {createStore} from 'redux';

/*  user imports    */
import testReducer from '../reducers/index';

let store = createStore(testReducer);

const Root = () => {
    return (
        <Provider store={store}>
            <div>Hello from Root component</div>
        </Provider>
    )
};

export default Root;
