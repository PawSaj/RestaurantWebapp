import React from 'react';
import BigCalendar from 'react-big-calendar';
import {Grid} from'react-bootstrap';
import moment from 'moment';
import events from './events';

BigCalendar.momentLocalizer(moment);

const BookLocal = () => {
    return (
        <div>
            <Grid>
                <BigCalendar
                    events={events}
                    views={['month']}
                    defaultDate={new Date(2015, 3, 1)}
                />
            </Grid>
        </div>
    )
};

export default BookLocal;