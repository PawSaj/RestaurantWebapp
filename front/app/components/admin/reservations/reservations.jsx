import React from 'react';
import {Grid} from 'react-bootstrap';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';
import {ADMIN_PATHS} from '../../../_consts/paths';
import RES_DATA from '../../../_consts/mocks/reservations';

const Reservations = ({passed}) => {
    let tableProps = {
        headsTitles: ['Id', 'Rezerwujący', 'Opis', 'Początek', 'Koniec'],
        modify: true,
        body: RES_DATA,
        links: {
            field: 'bookerId',
            path: ADMIN_PATHS.USERS
        }
    };

    let tabsProps = {
        id: "reservation-tab",
        tabs: [
            {
                title: 'Edycja rezerwacji',
                content: <CustomTable {...Object.assign({}, tableProps, passed)}/>
            }
        ],
    };

    return <Grid><CustomTabs {...tabsProps}/></Grid>;
};


export default Reservations;