import React from 'react';
import {Button} from 'react-bootstrap';
import FieldGroup from '../../_custom/fieldGroup';

const NewTableForm = () => {
    return (
        <div className="edit-form">
            <FieldGroup
                id="name"
                type="text"
                label="Nazwa"
                placeholder="Wprowadź nazwę"
            />
            <FieldGroup
                id="amount"
                type="text"
                label="Liczba osób"
                placeholder="Wprowadź liczbę osób"
            />
            <Button bsClass="btn btn-panel">Zapisz</Button>
        </div>
    );
};

export default NewTableForm;