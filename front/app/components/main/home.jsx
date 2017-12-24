import React from 'react';
import {Button, Modal, Grid, Row, Col} from 'react-bootstrap';
import Login from './login';
import Registration from './registration';

const Home = () => {
    let modalTitle = 'Rejestracja';
    return (
        <Grid>
            {/*<Modal show={true}>*/}
                {/*<Modal.Header closeButton>*/}
                    {/*<Modal.Title>{modalTitle}</Modal.Title>*/}
                {/*</Modal.Header>*/}
                {/*<Modal.Body>*/}
                   {/*<Login/>*/}
                    {/*<Registration/>*/}
                {/*</Modal.Body>*/}
            {/*</Modal>*/}

            <div className="greeting-box">
                <div className="description">
                    <h2>Witaj na stronie naszej reasturacji</h2>
                    <p>Zaloguj się, a jeśli nie masz jeszcze konta to zarejestruj się!</p>
                </div>
                <Button>Logowanie</Button>
                <Button>Rejestracja</Button>
            </div>
        </Grid>
    );
};

export default Home;