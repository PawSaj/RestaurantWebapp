import React from 'react';
import {Grid} from 'react-bootstrap';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';
import USERS from '../../../_consts/mocks/users';


const Users = ({passed}) => {

    let tableProps = {
        headsTitles: ['Id', 'Imię', 'Nazwisko', 'Rola'],
        modify: true,
        body: USERS
    };

    let tabsProps = {
        id: "users-tab",
        tabs: [
            {
                title: 'Edycja użytkowników',
                content: <CustomTable {...Object.assign({}, tableProps, passed)}/>
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