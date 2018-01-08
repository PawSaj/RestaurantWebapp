import React from 'react';
import {Button, Row, Col} from 'react-bootstrap';
import FieldGroup from '../_custom/fieldGroup';
import Dropzone from 'react-dropzone';
import {serializeFrom} from '../../lib/helpers/formHelpers';
import base64 from 'base-64';

const handleSubmission = (event, callback) => {
    event.preventDefault();
    let serializedObj = serializeFrom({formId: 'registration'});
    callback(serializedObj);
};


const Registration = ({callback, success}) => {
    let avatar = null;

    const onDrop = (files) => {
        avatar = files[0];
    };

    return (
        <form id="registration">
            {!success.status && <div className="error-form-msg">{success.errorText}</div>}
            <Row>
                <Col xs={5}>
                    <Dropzone
                        inputProps={{id: 'image'}}
                        className="dropzone"
                        accept="image/jpg"
                        multiple={false}
                        onDrop={onDrop}
                    >
                        <Button>Dodaj zdjęcie</Button>
                    </Dropzone>
                </Col>
                <Col xs={7}>
                    <FieldGroup
                        id="name"
                        type="text"
                        label="Imię"
                        placeholder="Wprowadź swoje imię"
                    />
                    <FieldGroup
                        id="surname"
                        type="text"
                        label="Nazwisko"
                        placeholder="Wprowadź swoje nazwisko"
                    />
                    <FieldGroup
                        id="phone"
                        type="text"
                        label="Numer telefonu"
                        placeholder="Wprowadź swój numer telefonu"
                    />
                </Col>
            </Row>

            <Row>
                <Col xs={12}>
                    <FieldGroup
                        id="email"
                        type="email"
                        label="Adres email"
                        placeholder="Wprowadź swoj adres email"
                    />
                </Col>
            </Row>

            <Row>
                <Col xs={6}>
                    <FieldGroup
                        id="password"
                        type="password"
                        label="Hasło"
                    />
                </Col>
                <Col xs={6}>
                    <FieldGroup
                        id="passwordConfirm"
                        type="password"
                        label="Potwierdzenie hasła"
                    />
                </Col>
            </Row>
            <div className="form-button">
                <Button type="submit" onClick={(event) => handleSubmission(event, callback)}>
                    Rejestracja
                </Button>
            </div>
        </form>
    )
};

export default Registration;