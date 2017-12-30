import React from 'react';
import ReactDOM from 'react-dom';
import {AppContainer} from 'react-hot-loader'
import {createStore} from 'redux'

/*  user imports    */
import '../assets/styles/main.scss'
import Routes from './Routes';
import rootReducer from './reducers/index';

let store = createStore(rootReducer);

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