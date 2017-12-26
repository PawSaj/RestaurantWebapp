import React from 'react';
import {NavLink} from 'react-router-dom'
import {MANAGER_PATHS} from '../../_consts/paths';
import CustomPanel from '../_custom/panel';

const ManagerPanel = () => {

    let panelProperties = {
        header: <h2>Witaj w panelu kierownika</h2>,
        buttons: [
            <NavLink exact to={MANAGER_PATHS.PROMOTIONS} activeClassName="active">Promocje</NavLink>,
            <NavLink exact to={MANAGER_PATHS.REPORTS} activeClassName="active">Raporty</NavLink>
        ]
    };

    return <CustomPanel {...panelProperties}/>;
};

export default ManagerPanel;