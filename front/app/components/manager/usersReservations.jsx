import React from 'react';

class UserReservations extends React.Component {
    constructor(props) {
        super(props);
        this.passed = props.passed;
        this.mealOrders = props.passed.managerFunctions.mealOrders;
        this.state = {
            orders: props.passed.manager.orders.data,
            startDate: '',
            endDate: ''
        };
        this.handleOnClick = this.handleOnClick.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.renderChart = this.renderChart.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({orders: nextProps.passed.manager.orders.data})
    }

    handleOnClick() {
        this.mealOrders(this.state.startDate, this.state.endDate);
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        newValue[id] = value;
        this.setState(newValue);
    }

    renderChart() {
        return (
            <div className="chart-area">
                <h3>Liczba złożonych zamówień miedzy {this.state.orders[0].date}
                    - {this.state.orders[Object.keys(this.state.orders).length - 1].date}</h3>
                <BarChart width={500} height={300} data={this.state.orders}>
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
                {this.state.orders && this.renderChart()}
            </Grid>
        );
    }
}

export default UserReservations;