import React from 'react';
import {BarChart, Bar, CartesianGrid, XAxis, YAxis} from 'recharts';
import {Grid, Button, Row, Col} from 'react-bootstrap';
import FieldGroup from '../_custom/fieldGroup';

class ReservationTraffic extends React.Component {
    constructor(props) {
        super(props);
        this.passed = props.passed;
        this.reservationTraffic = props.passed.managerFunctions.reservationTraffic;
        this.state = {
            traffic: props.passed.manager.traffic.data,
            startDate: '',
            endDate: ''
        };
        this.handleOnClick = this.handleOnClick.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.renderChart = this.renderChart.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({traffic: nextProps.passed.manager.traffic.data})
    }

    handleOnClick() {
        this.reservationTraffic(this.state.startDate, this.state.endDate);
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        newValue[id] = value;
        this.setState(newValue);
    }

    renderChart() {
        return (
            <div className="chart-area">
                <h3>Ruch w restauracji</h3>
                <BarChart width={500} height={300} data={this.state.traffic}>
                    <CartesianGrid stroke="#ccc"/>
                    <XAxis dataKey="date"/>
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
                    <Col xs={6}>
                        <FieldGroup
                            id="startDate"
                            type="text"
                            label="Data początkowa"
                            placeholder="Wprowadź datę początkową"
                            value={this.state.startDate}
                            onChange={this.handleChange}
                        />
                    </Col>
                    <Col xs={6}>
                        <FieldGroup
                            id="endDate"
                            type="text"
                            label="Data końcowa"
                            placeholder="Wprowadź datę końcową"
                            value={this.state.endDate}
                            onChange={this.handleChange}
                        />
                    </Col>
                </Row>
                <Button bsClass="btn btn-panel" onClick={this.handleOnClick}>Szukaj</Button>
                {this.state.traffic && this.renderChart()}
            </Grid>
        );
    }
}

export default ReservationTraffic;