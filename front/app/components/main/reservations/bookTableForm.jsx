import React from 'react';
import FieldGroup from '../../_custom/fieldGroup';
import {Button} from 'react-bootstrap';
import momentTimezone from 'moment-timezone';

class bookTableForm extends React.Component {
    constructor(props) {
        super(props);
        this.callback = props.callback;
        this.m = momentTimezone.tz(props.passed, "Europe/Berlin");
        this.state = {
            date: this.m.tz("Europe/Berlin").format(),
            id: ''
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        newValue[id] = value;
        this.setState(newValue);
    }


    render() {
        return (
            <form id="book-table-form">
                <FieldGroup
                    id="restaurantReservationDate "
                    type="text"
                    label="Data rezerwacji"
                    disabled={true}
                    value={this.state.date}
                />
                <FieldGroup
                    id="id"
                    type="text"
                    label="Id stolika"
                    onChange={this.handleChange}
                    value={this.state.id}
                />
                <Button onClick={() => this.callback({
                    tableReservationDate: this.state.date,
                    table: {id: this.state.id}
                })}>Potwierdż</Button>
            </form>
        )
    }

}

export default bookTableForm;