import React from 'react';
import {Grid, PageHeader, Button, FormGroup, FormControl, ControlLabel, Row, Col} from 'react-bootstrap';
import Dropzone from 'react-dropzone';

const Profile = () => {
    let avatar = null;

    const onDrop = (files) => {
        avatar = files[0];
    };

    return (
        <div className="profile">
            <Grid>
                <PageHeader>
                    Dane osobowe
                </PageHeader>
                <form>
                    <Row>
                        <Col xs={6} md={4}>
                            <Dropzone
                                className="dropzone"
                                accept="image/jpg"
                                multiple={false}
                                onDrop={onDrop}
                            >
                                <Button>Zmień zdjęcie</Button>
                            </Dropzone>
                        </Col>
                        <Col xs={6} md={8}>
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
                            <FormGroup controlId="email" className="email-input">
                                <ControlLabel>Adres email:</ControlLabel>
                                <FormControl disabled type="email"/>
                            </FormGroup>
                        </Col>
                    </Row>

                    <Row>
                        <Col xs={12} md={6}>
                            <FormGroup controlId="password">
                                <ControlLabel>Hasło:</ControlLabel>
                                <FormControl type="password"/>
                            </FormGroup>
                        </Col>
                        <Col xs={12} md={6}>
                            <FormGroup controlId="passwordConfirm">
                                <ControlLabel>Potwierdzenie hasła:</ControlLabel>
                                <FormControl type="password"/>
                            </FormGroup>
                        </Col>
                    </Row>
                    <div className="form-button">
                        <Button type="submit">
                            Zmień dane
                        </Button>
                    </div>
                </form>
            </Grid>
        </div>
    )
};

export default Profile;