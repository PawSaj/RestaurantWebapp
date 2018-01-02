import React from "react";
import {Button, Grid, Row, Col} from "react-bootstrap";
import FieldGroup from "../../_custom/fieldGroup";

const User = ({passed}) => {

    return (
        <Grid className="edit-form">
            <Row>
                <Col xs={6} md={4}>
                    <FieldGroup
                        id="name"
                        label="Imię"
                        type="text"
                        placeholder="Wprowadź imię"
                    />
                </Col>
                <Col xs={6} md={4}>
                    <FieldGroup
                        id="surname"
                        label="Nazwisko"
                        type="text"
                        placeholder="Wprowadź nazwisko"
                    />
                </Col>
                <Col xs={12} md={4}>
                    <FieldGroup
                        id="phone"
                        label="Numer telefonu"
                        type="text"
                        placeholder="Wprowadź numer telefonu"
                    />
                </Col>
            </Row>
            <Row>
                <Col xs={6} md={8}>
                    <FieldGroup
                        id="email"
                        label="Adres email"
                        type="email"
                        disabled={true}
                    />
                </Col>
                <Col xs={6} md={4}>
                    <FieldGroup
                        id="role"
                        label="Rola"
                        componentClass="select"
                        placeholder="user"
                    >
                        <option value="user">użytkownik</option>
                        <option value="admin">administrator</option>
                        <option value="manager">kierownik</option>
                    </FieldGroup>
                </Col>
            </Row>
            <Row>
                <Col xs={12}>
                    <Button bsClass="btn btn-panel">Zapisz</Button>
                </Col>
            </Row>
        </Grid>
    );

};

export default User;