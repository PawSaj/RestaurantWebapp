import React from 'react';
import FieldGroup from '../_custom/fieldGroup';
import {Button} from 'react-bootstrap';
import {serializeFrom} from '../../lib/helpers/formHelpers';

const handleSubmission = (event, callback) => {
    event.preventDefault();
    let serializedObj = serializeFrom({formId: 'login-form'});
    callback();
};


const Login = ({callback, pending}) => {
    let button =
        <Button type="submit" onClick={(event) => handleSubmission(event, callback)}>
            Logowanie
        </Button>;

    if(pending === true){
        button =
            <Button type="submit" onClick={(event) => handleSubmission(event, callback)} disabled>
                ...
            </Button>;
    }

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
                {button}
            </div>
        </form>
    )
};

export default Login;