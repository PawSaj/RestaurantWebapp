import React from 'react';
import {Button, FormGroup, FormControl, ControlLabel, Row, Col} from 'react-bootstrap';
import Dropzone from 'react-dropzone';
import base64 from 'base-64';

const Registration = () => {
    let avatar = null;

    const onDrop = (files) => {
        avatar = files[0];
    };

    return (
        <form id="registration">
            <Row>
                <Col xs={5}>
                    <Dropzone
                        className="dropzone"
                        accept="image/jpg"
                        multiple={false}
                        onDrop={onDrop}
                    >
                        <Button>Dodaj zdjęcie</Button>
                    </Dropzone>
                </Col>
                <Col xs={7}>
                    <FormGroup controlId="name">
                        <ControlLabel>Imię:</ControlLabel>
                        <FormControl placeholder="Wprowadź swoje imię" type="text"/>
                    </FormGroup>
                    <FormGroup controlId="surname">
                        <ControlLabel>Nazwisko:</ControlLabel>
                        <FormControl placeholder="Wprowadź swoje nazwisko" type="text"/>
                    </FormGroup>
                    <FormGroup controlId="phone">
                        <ControlLabel>Numer telefonu:</ControlLabel>
                        <FormControl placeholder="Wprowadź swój numer telefonu" type="text"/>
                    </FormGroup>
                </Col>
            </Row>

            <Row>
                <Col xs={12}>
                    <FormGroup controlId="email">
                        <ControlLabel>Adres email:</ControlLabel>
                        <FormControl placeholder="Wprowaź swój adres email" type="email"/>
                    </FormGroup>
                </Col>
            </Row>

            <Row>
                <Col xs={6}>
                    <FormGroup controlId="password">
                        <ControlLabel>Hasło:</ControlLabel>
                        <FormControl type="password"/>
                    </FormGroup>
                </Col>
                <Col xs={6}>
                    <FormGroup controlId="passwordConfirm">
                        <ControlLabel>Potwierdzenie hasła:</ControlLabel>
                        <FormControl type="password"/>
                    </FormGroup>
                </Col>
            </Row>
            <div className="form-button">
                <Button type="submit">
                    Rejestracja
                </Button>
            </div>
        </form>
    )
};

export default Registration;