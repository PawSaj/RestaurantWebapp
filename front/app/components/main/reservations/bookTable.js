import React from 'react';
import TableCalendar from 'react-big-calendar';
import {Grid} from'react-bootstrap';
import moment from 'moment';
import events from '../../../_consts/mocks/events';

moment.locale('pl');
TableCalendar.momentLocalizer(moment);

class BookTable extends React.Component {
    componentDidMount() {
        let buttons = document.getElementsByTagName('button');
        buttons[5].click();
        for(let key in buttons){
            if(buttons.hasOwnProperty(key)){
                buttons[key].remove();
            }
        }
    }

    render() {
        return (
            <div>
                <Grid>
                    <TableCalendar
                        events={events}
                        popup
                        selectable
                        views={['month', 'work_week']}
                        startAccessor='start'
                        endAccessor='end'
                        defaultDate={moment().toDate()}
                    />
                </Grid>
            </div>
        )
    }
}

export default BookTable;