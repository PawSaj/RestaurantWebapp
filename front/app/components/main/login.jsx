import React from 'react';
import FieldGroup from '../_custom/fieldGroup';
import {Button} from 'react-bootstrap';
import {serializeFrom} from '../../lib/helpers/formHelpers';

const handleSubmission = (event) => {
    event.preventDefault();
    let serializedObj = serializeFrom({formId: 'login-form'});
    console.log('serializedObj: ', serializedObj);
};


const Login = () => {

    return (
        <form id="login-form">
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
                <Button type="submit" onClick={handleSubmission}>
                    Logowanie
                </Button>
            </div>
        </form>
    )
};

export default Login;