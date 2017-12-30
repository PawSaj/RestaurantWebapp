import React from 'react';
import {Grid, PageHeader, Button, Row, Col} from 'react-bootstrap';
import FieldGroup from '../_custom/fieldGroup';
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
                                className="email-input"
                                disabled={true}
                            />
                        </Col>
                    </Row>

                    <Row>
                        <Col xs={12} md={6}>
                            <FieldGroup
                                id="password"
                                type="password"
                                label="Hasło"
                            />
                        </Col>
                        <Col xs={12} md={6}>
                            <FieldGroup
                                id="passwordConfirm"
                                type="password"
                                label="Potwierdzenie hasła"
                            />
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