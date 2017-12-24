import React from 'react';
import {Button, FormGroup, FormControl, ControlLabel} from 'react-bootstrap';

const Login = () => {
    return (
        <form>
            <FormGroup controlId="email">
                <ControlLabel>Adres email:</ControlLabel>
                <FormControl placeholder="Wprowaź swój adres email" type="email"/>
            </FormGroup>
            <FormGroup controlId="password">
                <ControlLabel>Hasło:</ControlLabel>
                <FormControl type="password"/>
            </FormGroup>
            <div className="form-button">
                <Button type="submit">
                    Logowanie
                </Button>
            </div>
        </form>
    )
};

export default Login;