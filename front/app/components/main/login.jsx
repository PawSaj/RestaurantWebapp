import React from 'react';
import FieldGroup from '../_custom/fieldGroup';
import {Button} from 'react-bootstrap';

const Login = () => {
    return (
        <form>
            <FieldGroup
                id="email"
                type="email"
                label="Adres email"
                placeholder="Wprowadź swoj adres email"
            />
            <FieldGroup
                id="password"
                type="password"
                label="Hasło"
                placeholder="Wprowadź swoje hasło"
            />
            <div className="form-button">
                <Button type="submit">
                    Logowanie
                </Button>
            </div>
        </form>
    )
};

export default Login;