import React from 'react';
import ReactDOM from 'react-dom';
import {AppContainer} from 'react-hot-loader'
import SidePanel from './containers/SidePanel';

const render = Component => {
    ReactDOM.render(
        <AppContainer>
            <Component />
        </AppContainer>,
        document.getElementById('root'),
    )
};

render(SidePanel);

if (module.hot) {
    module.hot.accept('./containers/SidePanel', () => {
        render(SidePanel)
    })
}