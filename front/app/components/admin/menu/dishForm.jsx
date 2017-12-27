import React from 'react';
import {Button, Grid, Col, Row} from 'react-bootstrap';
import FieldGroup from '../../_custom/fieldGroup';

const DishForm = ({values = null} = {}) => {
    return (
        <Grid className="edit-form">
            <FieldGroup
                id="name"
                type="text"
                label="Nazwa"
                placeholder="Wprowadź nazwę"
                value={values ? values.name : ''}
            />
            <FieldGroup
                id="category"
                label="Kategoria"
                componentClass="select"
                placeholder="pizza"
                value={values ? values.category : null}>
                <option value="pizza">Pizza</option>
                <option value="soup">Zupa</option>
            </FieldGroup>
            <FieldGroup
                id="ingredients"
                label="Składniki"
                componentClass="textarea"
                placeholder="Uzupełnij składniki"
                value={values ? values.ingredients : ''}
            />
            <FieldGroup
                id="price"
                type="text"
                label="Cena"
                placeholder="Wprowadź cenę"
                value={values ? values.price : ''}
            />
            <Button bsClass="btn btn-panel">Zapisz</Button>
        </Grid>
    );
};

export default DishForm;