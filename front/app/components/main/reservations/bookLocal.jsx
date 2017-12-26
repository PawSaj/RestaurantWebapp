import React from 'react';
import EventCalendar from 'react-big-calendar';
import {Grid} from'react-bootstrap';
import moment from 'moment';
import events from '../../../_consts/_events';

moment.locale('pl');
EventCalendar.momentLocalizer(moment);

const BookLocal = () => {
    return (
        <div>
            <Grid>
                <EventCalendar
                    events={events}
                    views={['month']}
                    startAccessor='start'
                    endAccessor='end'
                    defaultDate={moment().toDate()}
                />
            </Grid>
        </div>
    )
};

export default BookLocal;