import React from 'react';
import {Button, Grid, Col, Row} from 'react-bootstrap';
import FieldGroup from '../../_custom/fieldGroup';

const TableForm = () => {
    let values = {};
    return (
        <Grid className="edit-form">
            <Row>
                <Col xs={6}>
                    <FieldGroup
                        id="name"
                        type="text"
                        label="Nazwa"
                        placeholder="Wprowadź nazwę"
                        value={values ? values.name : ''}
                    />
                </Col>
                <Col xs={6}>
                    <FieldGroup
                        id="amount"
                        type="text"
                        label="Liczba osób"
                        placeholder="Wprowadź liczbę osób"
                        value={values ? values.name : ''}
                    />
                </Col>
            </Row>
            <Row>
                <Col xs={12}>
                    <Button bsClass="btn btn-panel">Zapisz</Button>
                </Col>
            </Row>
        </Grid>
    )
};

export default TableForm;