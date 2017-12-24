import React from 'react';
import {Button, Grid} from 'react-bootstrap';
import {NavLink, Switch, Route} from 'react-router-dom';
import {RESERVATIONS_PATHS} from '../consts/paths';

const Reservation = (props) => {
    console.log('reservation props: ', props);

    let url = props.match.url;

    return (
        <Grid>
            <div className="greeting-box">
                <div className="description">
                    <h2>Rezerwacje</h2>
                    <p>W naszej restauracji możliwa jest rezerwacja całego lokalu
                        na imprezy okolicznościowe, bądź pojedyńczego stolika</p>
                </div>
                <Button><NavLink exact to={url  + RESERVATIONS_PATHS.LOCAL}>Lokal</NavLink></Button>
                <Button><NavLink exact to={url  + RESERVATIONS_PATHS.TABLE}>Stolik</NavLink></Button>
            </div>
        </Grid>
    )
};

export default Reservation;