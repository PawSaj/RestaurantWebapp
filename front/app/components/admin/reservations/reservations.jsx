import React from 'react';
import {Grid} from 'react-bootstrap';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';
import {ADMIN_PATHS} from '../../../_consts/paths';
import RES_DATA from '../../../_consts/mocks/reservations';

const Reservations = () => {
    let tableProps = {
        headsTitles: ['Rezerwujący', 'Opis', 'Początek', 'Koniec'],
        modify: true,
        body: RES_DATA,
        links: {
            filed: 'bookerId',
            path: ADMIN_PATHS.USERS
        }
    };

    let tabsProps = {
        id: "reservation-tab",
        tabs: [
            {
                title: 'Edycja rezerwacji',
                content: <CustomTable {...tableProps}/>
            }
        ],
    };

    return <Grid><CustomTabs {...tabsProps}/></Grid>;
};


export default Reservations;