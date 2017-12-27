import React from 'react';
import {Button, Grid, Row, Col} from 'react-bootstrap';
import FieldGroup from '../../_custom/fieldGroup';

const ReservationForm = ({values = null} = {}) => {
    return (
        <Grid className="edit-form">
            <Row>
                <Col xs={12}>
                    <FieldGroup
                        id="booker"
                        type="text"
                        label="Rezerwujący"
                        disabled={true}
                        value={values ? values.bookerId : ''}
                    />
                </Col>
            </Row>
            <Row>
                <Col xs={12}>
                    <FieldGroup
                        id="desc"
                        label="Opis"
                        componentClass="textarea"
                        value={values ? values.desc : ''}
                    />
                </Col>
            </Row>
            <Row>
                <Col xs={6}>
                    <FieldGroup
                        id="start"
                        label="Data rozpoczęcia"
                        type="text"
                        value={values ? values.start : ''}
                    />
                </Col>
                <Col xs={6}>
                    <FieldGroup
                        id="end"
                        label="Data zakonczenia"
                        type="text"
                        value={values ? values.end : ''}
                    />
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

export default ReservationForm;