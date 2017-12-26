import React from 'react';
import CustomPanel from '../custom/panel';
import Login from './login';
import Registration from './registration';

const Home = () => {
    let modalTitle = 'Rejestracja';
    let panelProperties = {
        header: <div className="description">
            <h2>Witaj na stronie naszej reasturacji</h2>
            <p>Zaloguj się, a jeśli nie masz jeszcze konta to zarejestruj się!</p>
        </div>,
        buttons: [
            'Logowanie',
            'Rejestracja'
        ]
    };

    return (
        <div id="home">
            {/*<Modal show={true}>*/}
            {/*<Modal.Header closeButton>*/}
            {/*<Modal.Title>{modalTitle}</Modal.Title>*/}
            {/*</Modal.Header>*/}
            {/*<Modal.Body>*/}
            {/*/!*<Login/>*!/*/}
            {/*/!*<Registration/>*!/*/}
            {/*</Modal.Body>*/}
            {/*</Modal>*/}
            <CustomPanel {...panelProperties}/>
        </div>
    );
};

export default Home;