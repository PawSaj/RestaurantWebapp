import React from 'react';
import {NavLink} from 'react-router-dom'
import {MANAGER_PATHS} from '../../_consts/paths';
import CustomPanel from '../_custom/panel';

const ManagerPanel = (props) => {
    console.log('props: ', props)

    let panelProperties = {
        header: <h2>Witaj w panelu kierownika</h2>,
        buttons: [
            {
                name: <NavLink exact to={MANAGER_PATHS.TABLE} activeClassName="active">Raporty stolików</NavLink>
            }, {
                name: <NavLink exact to={MANAGER_PATHS.TRAFFIC} activeClassName="active">Raporty przepustowości</NavLink>
            }, {
                name: <NavLink exact to={MANAGER_PATHS.ORDER} activeClassName="active">Raporty zamówień</NavLink>
            }, {
                name: <NavLink exact to={MANAGER_PATHS.RESERVATION} activeClassName="active">Raporty rezerwacji</NavLink>
            }
        ]
    };

    return <CustomPanel {...panelProperties}/>;
};

export default ManagerPanel;