import React from 'react';
import FieldGroup from '../../_custom/fieldGroup';
import {Button} from 'react-bootstrap';


const bookLocalForm = ({date, callback}) => {
    return (
        <form id="book-local-form">
            <FieldGroup
                id="restaurantReservationDate "
                type="text"
                label="Chcesz zarezerwować lokal na:"
                disabled={true}
                value={date}
            />
            <Button onClick={() => callback({restaurantReservationDate: date})}>Potwierdż</Button>
        </form>
    )
};

export default bookLocalForm;