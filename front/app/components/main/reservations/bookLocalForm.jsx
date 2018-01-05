import React from 'react';
import FieldGroup from '../../_custom/fieldGroup';
import {Button} from 'react-bootstrap';


const bookLocalForm = ({date}) => {
    console.log(date);
    return (
        <form id="book-local-form">
            <FieldGroup
                id="restaurantReservationDate "
                type="text"
                label="Chcesz zarezerwować lokal na:"
                disabled={true}
                value={date}
            />
            <Button>Potwierdż</Button>
        </form>
    )
};

export default bookLocalForm;