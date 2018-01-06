import React from 'react';
import {NavLink} from 'react-router-dom'
import {ADMIN_PATHS} from '../../_consts/paths';
import CustomPanel from '../_custom/panel';

const AdminPanel = ({passed}) => {
    console.log('admin panel', passed)
    let panelProperties = {
        header: <h2>Witaj w panelu administarcyjnym</h2>,
        buttons: [
            {
                name: <NavLink exact to={ADMIN_PATHS.RESERVATIONS} activeClassName="active">Rezerwacje</NavLink>
            }, {
                name: <NavLink exact to={ADMIN_PATHS.TABLES} activeClassName="active">Stoliki</NavLink>
            }, {
                name: <NavLink exact to={ADMIN_PATHS.MENU} activeClassName="active">Menu</NavLink>
            }, {
                name: <NavLink exact to={ADMIN_PATHS.USERS} activeClassName="active">Użytkownicy</NavLink>
            }
        ]
    };

    return <CustomPanel {...panelProperties}/>;
};

export default AdminPanel;