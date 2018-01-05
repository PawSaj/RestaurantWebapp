import React from 'react';
import EventCalendar from 'react-big-calendar';
import {Grid} from'react-bootstrap';
import moment from 'moment';
import events from '../../../_consts/mocks/events';
import Modal from '../../_custom/modal';
import BookLocalForm from './bookLocalForm';

moment.locale('pl');
EventCalendar.momentLocalizer(moment);

class BookLocal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            selectedDate: ''
        }

    }

    selected(data) {
        console.log('data: ', data);
        const myDate = moment(data.start).format('YYYY-MM-DD');
        this.setState({showModal: true, selectedDate: myDate})
    }

    componentWillReceiveProps() {
        this.setState({showModal: false, selectedDate: ''});
    }

    render() {
        return (
            <div>
                <Grid>
                    <EventCalendar
                        events={events}
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
                            body: <BookLocalForm date={this.state.selectedDate}/>,
                            show: this.state.showModal
                        }}/>
                </Grid>
            </div>
        )
    }
}

export default BookLocal;