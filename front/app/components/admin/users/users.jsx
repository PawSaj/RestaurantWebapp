import React from 'react';
import {Grid} from 'react-bootstrap';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';
import USERS from '../../../_consts/mocks/users';
import {ADMIN_PATHS} from '../../../_consts/paths';


const Users = (props) => {
    let {match} = props;

    let tableProps = {
        headsTitles: ['Id', 'Imię', 'Nazwisko', 'Rola'],
        modify: true,
        body: USERS,
        links: {
            filed: 'id',
            path: ADMIN_PATHS.USERS
        }
    };

    let tabsProps = {
        id: "users-tab",
        tabs: [
            {
                title: 'Edycja użytkowników',
                content: <CustomTable {...tableProps}/>
            }
        ],
    };

    return (
        <Grid>
            <CustomTabs {...tabsProps}/>
        </Grid>
    );
};

export default Users;