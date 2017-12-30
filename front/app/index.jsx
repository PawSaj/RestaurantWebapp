import React from 'react';
import ReactDOM from 'react-dom';
import {AppContainer} from 'react-hot-loader';
import {createStore, applyMiddleware} from 'redux';
import thunkMiddleware from 'redux-thunk'

/*  user imports    */
import '../assets/styles/main.scss';
import Routes from './Routes';
import rootReducer from './reducers/index';

let store = createStore(rootReducer, applyMiddleware(thunkMiddleware));

const render = Component => {
    ReactDOM.render(
        <AppContainer>
            <Component store={store}/>
        </AppContainer>,
        document.getElementById('root'),
    )
};

render(Routes);

if (module.hot) {
    module.hot.accept('./Routes', () => {
        render(Routes);
    })
}