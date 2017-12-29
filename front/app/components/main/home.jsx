import React from 'react';
import CustomPanel from '../_custom/panel';
import Login from './login';
import Registration from './registration';
import Modal from '../_custom/modal';


const loginCallback = () => {
    console.log('h1');
};

const registrationCallback = () => {
    console.log('h2');
};

const Home = () => {
    let panelProperties = {
        header: <div className="description">
            <h2>Witaj na stronie naszej reasturacji</h2>
            <p>Zaloguj się, a jeśli nie masz jeszcze konta to zarejestruj się!</p>
        </div>,
        buttons: [
            {
                name: 'Logowanie',
                callback: () => loginCallback()
            }, {
                name: 'Rejestracja',
                callback: () => registrationCallback()
            }
        ]
    };

    return (
        <div id="home">
            {/*<Modal {...{title: 'Logowanie', body: <Login/>, show: true}}/>*/}
            {/*<Modal {...{title: 'Rejestracja', body: <Registration/>, show: true}}/>*/}
            <CustomPanel {...panelProperties}/>
        </div>
    );
};

export default Home;