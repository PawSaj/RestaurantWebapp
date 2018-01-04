import React from 'react';
import {Button, Grid, Col, Row} from 'react-bootstrap';
import FieldGroup from '../../_custom/fieldGroup';
import {getIDFromPath} from '../../../lib/helpers/urlHelpers';

const getTable = (path, tables) => {
    if (tables === undefined) {
        return null;
    }
    let tableID = getIDFromPath(path);
    for (let table of tables) {
        if (table.id === tableID) {
            return table;
        }
    }
    return null;
};

const setTableData = (table) => {
    if (table === null) {
        return {
            id: null,
            floor: '',
            seats: '',
            tableNumber: ''
        };
    }

    return {
        id: (table.id) ? table.id : null,
        floor: (table.floor) ? table.floor : '',
        seats: (table.seats) ? table.seats : '',
        tableNumber: (table.tableNumber) ? table.tableNumber : ''
    }
};


class TableForm extends React.Component {
    constructor(props) {
        super(props);
        this.new = props.new;
        this.createTable = props.passed.adminFunctions.createTable;
        this.table = this.new? null :getTable(props.passed.location.pathname, props.passed.admin.tables.data);
        this.state = setTableData(this.table);
        this.getTableByID = props.passed.adminFunctions.getTableByID;
        this.updateTable = props.passed.adminFunctions.updateTable;

        if (!this.new  && this.table === null) {
            let tableID = getIDFromPath(props.passed.location.pathname);
            this.getTableByID(tableID);
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleOnClick = this.handleOnClick.bind(this);
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        newValue[id] = value;
        this.setState(newValue);
    }

    handleOnClick(event) {
        event.preventDefault();
        console.log(this.state)
        this.new? this.createTable(this.state): this.updateTable(this.state.id, this.state);
    }

    componentWillReceiveProps(nextProps) {
        this.table = getTable(nextProps.passed.location.pathname, nextProps.passed.admin.tables.data);
        this.state = setTableData(this.table);
    }


    render() {
        return (
            <Grid className="edit-form">
                <Row>
                    <Col xs={6}>
                        <FieldGroup
                            id="tableNumber"
                            type="text"
                            label="Numer stolika"
                            placeholder="Wprowadź numer stolika"
                            value={this.state.tableNumber}
                            onChange={this.handleChange}
                        />
                    </Col>
                    <Col xs={6}>
                        <FieldGroup
                            id="seats"
                            type="text"
                            label="Liczba miejsc"
                            placeholder="Wprowadź liczbę miejsc"
                            value={this.state.seats}
                            onChange={this.handleChange}
                        />
                    </Col>
                </Row>
                <Row>
                    <Col xs={12}>
                        <FieldGroup
                            id="floor"
                            type="text"
                            label="Piętro"
                            placeholder="Wprowadź piętro"
                            value={this.state.floor}
                            onChange={this.handleChange}
                        />
                    </Col>
                </Row>
                <Row>
                    <Col xs={12}>
                        <Button bsClass="btn btn-panel" onClick={this.handleOnClick}>Zapisz</Button>
                    </Col>
                </Row>
            </Grid>
        )
    }
}


export default TableForm;