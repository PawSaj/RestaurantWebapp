import React from 'react';
import {BarChart, Bar, CartesianGrid, XAxis, YAxis} from 'recharts';
import {Grid, Button, Row, Col} from 'react-bootstrap';
import FieldGroup from '../_custom/fieldGroup';

class UserReservations extends React.Component {
    constructor(props) {
        super(props);
        this.passed = props.passed;
        this.usersReservations = props.passed.managerFunctions.usersReservations;
        this.state = {
            reservations: props.passed.manager.reservations.data,
            startDate: '',
            endDate: '',
            topNumber: ''
        };
        this.handleOnClick = this.handleOnClick.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.renderChart = this.renderChart.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({reservations: nextProps.passed.manager.reservations.data})
    }

    handleOnClick() {
        this.usersReservations(this.state.startDate, this.state.endDate, this.state.topNumber);
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        newValue[id] = value;
        this.setState(newValue);
    }

    renderChart() {
        return (
            <div className="chart-area">
                <h3>Rezerwacje</h3>
                <BarChart width={500} height={300} data={this.state.reservations}>
                    <CartesianGrid stroke="#ccc"/>
                    <XAxis dataKey="type"/>
                    <YAxis />
                    <Bar dataKey="sum" fill="#8884d8"/>
                </BarChart>
            </div>
        );
    }


    render() {
        return (
            <Grid className="edit-form">
                <Row>
                    <Col xs={4}>
                        <FieldGroup
                            id="startDate"
                            type="text"
                            label="Data początkowa"
                            placeholder="Wprowadź datę początkową"
                            value={this.state.startDate}
                            onChange={this.handleChange}
                        />
                    </Col>
                    <Col xs={4}>
                        <FieldGroup
                            id="endDate"
                            type="text"
                            label="Data końcowa"
                            placeholder="Wprowadź datę końcową"
                            value={this.state.endDate}
                            onChange={this.handleChange}
                        />
                    </Col>
                    <Col xs={4}>
                        <FieldGroup
                            id="topNumber"
                            type="text"
                            label="Liczba userów"
                            placeholder="Wprowadź liczbę userów"
                            value={this.state.topNumber}
                            onChange={this.handleChange}
                        />
                    </Col>
                </Row>
                <Button bsClass="btn btn-panel" onClick={this.handleOnClick}>Szukaj</Button>
                {this.state.reservations && this.renderChart()}
            </Grid>
        );
    }
}

export default UserReservations;