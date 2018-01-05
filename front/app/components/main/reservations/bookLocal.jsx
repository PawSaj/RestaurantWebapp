import React from 'react';
import EventCalendar from 'react-big-calendar';
import {Grid} from'react-bootstrap';
import moment from 'moment';
import Modal from '../../_custom/modal';
import BookLocalForm from './bookLocalForm';

moment.locale('pl');
EventCalendar.momentLocalizer(moment);

class BookLocal extends React.Component {
    constructor(props) {
        super(props);
        this.localReservation = props.passed.mainFunctions.localReservation;
        this.addLocalReservation = props.passed.mainFunctions.addLocalReservation;
        this.reservations = props.passed.main.reservations.local;
        this.state = {
            showModal: false,
            selectedDate: '',
            events: []
        };

        if (!this.reservations) {
            this.localReservation();
        }

        this.setEvents = this.setEvents.bind(this);
    }

    selected(data) {
        const myDate = moment(data.start).format('YYYY-MM-DD');
        for (let event of this.state.events) {
            if (moment(event.start).format('YYYY-MM-DD') === myDate || myDate <= moment().format('YYYY-MM-DD')) {
                return false;
            }
        }
        this.setState({showModal: true, selectedDate: myDate})
    }

    setEvents() {
        console.log('res: ', this.reservations);
        if (!this.reservations)
            return;

        let events = this.reservations.map((event) => {
            return {
                'title': 'Rezerwacja lokalu',
                'allDay': event.allDay,
                'start': moment(event.date).toDate(),
                'end': moment(event.date).toDate()
            }
        });
        this.setState({events: events});
    }

    componentWillReceiveProps(nextProps) {
        this.setState({showModal: false, selectedDate: ''});
        this.reservations = nextProps.passed.main.reservations.local;
        this.setEvents();
    }

    render() {
        return (
            <div>
                <Grid>
                    <EventCalendar
                        events={this.state.events}
                        views={['month']}
                        startAccessor='start'
                        endAccessor='end'
                        selectable={true}
                        defaultDate={moment().toDate()}
                        onSelectSlot={(data) => this.selected(data)}
                    />
                    <Modal
                        {...{
                            title: 'Rezerwacja lokalu',
                            body: <BookLocalForm date={this.state.selectedDate} callback={this.addLocalReservation}/>,
                            show: this.state.showModal
                        }}/>
                </Grid>
            </div>
        )
    }
}

export default BookLocal;