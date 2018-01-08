import React from 'react';
import TableCalendar from 'react-big-calendar';
import {Grid} from'react-bootstrap';
import moment from 'moment';
import Modal from '../../_custom/modal';
import BookTableForm from './bookTableForm';

moment.locale('pl');
TableCalendar.momentLocalizer(moment);

class BookTable extends React.Component {
    constructor(props) {
        super(props);
        this.tablesReservation = props.passed.mainFunctions.tablesReservation;
        this.addTableReservation = props.passed.mainFunctions.addTableReservation;
        this.reservations = props.passed.main.reservations.tables;
        this.state = {
            showModal: false,
            selectedDate: '',
            events: []
        };

        if (!this.reservations) {
            this.tablesReservation();
        }

        this.setEvents = this.setEvents.bind(this);
    }

    selected(data) {
        const myDate = moment(data.start).format('YYYY-MM-DD HH:mm');
        for (let event of this.state.events) {
            if (moment(event.start).format('YYYY-MM-DD HH:mm') === myDate || myDate <= moment().format('YYYY-MM-DD HH:mm')
            ) {
                return false;
            }
        }
        this.setState({showModal: true, selectedDate: myDate})
    }

    setEvents() {
        if (!this.reservations)
            return;

        let events = this.reservations.map((event) => {
            return {
                'title': 'Stolik numer ' + event.table.tableNumber,
                'start': moment(event.date).toDate(),
                'end': moment(event.date).add(2, "hours").toDate()
            }
        });
        this.setState({events: events});
    }

    componentWillReceiveProps(nextProps) {
        console.log('nextProps tables: ', nextProps);
        this.setState({showModal: false, selectedDate: ''});
        this.reservations = nextProps.passed.main.reservations.tables;
        this.setEvents();
    }

    componentDidMount() {
        let buttons = document.getElementsByTagName('button');
        buttons[5].click();
        for (let key in buttons) {
            if (buttons.hasOwnProperty(key)) {
                buttons[key].remove();
            }
        }
    }

    render() {
        return (
            <div>
                <Grid>
                    <TableCalendar
                        events={this.state.events}
                        popup
                        selectable={true}
                        views={['month', 'work_week']}
                        startAccessor='start'
                        endAccessor='end'
                        defaultDate={moment().toDate()}
                        onSelectSlot={(data) => this.selected(data)}
                    />
                    <Modal
                        {...{
                            title: 'Rezerwacja stolika',
                            body: <BookTableForm date={this.state.selectedDate} callback={this.addTableReservation}/>,
                            show: this.state.showModal
                        }}/>
                </Grid>
            </div>
        )
    }
}

export default BookTable;