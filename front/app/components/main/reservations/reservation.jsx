import React from 'react';
import {NavLink} from 'react-router-dom';
import CustomPanel from '../../_custom/panel';
import {RESERVATIONS_PATHS} from '../../../_consts/paths';

const Reservation = (props) => {
    let url = props.match.url;
    let panelProperties = {
        header: <div className="description">
            <h2>Rezerwacje</h2>
            <p>W naszej restauracji możliwa jest rezerwacja całego lokalu
                na imprezy okolicznościowe, bądź pojedyńczego stolika</p>
        </div>,
        buttons: [
            {
                name: <NavLink exact to={url + RESERVATIONS_PATHS.LOCAL}>Lokal</NavLink>
            }, {
                name: <NavLink exact to={url + RESERVATIONS_PATHS.TABLE}>Stolik</NavLink>
            }
        ]
    };

    return (
        <CustomPanel {...panelProperties}/>
    );
};

export default Reservation;