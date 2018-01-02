import React from 'react';
import CustomTable from '../../_custom/table';
import TABLES from '../../../_consts/mocks/tables';
import CustomTabs from '../../_custom/tabs';
import {Grid} from 'react-bootstrap';
import NewTable from './newTableForm';

const Tables = ({passed}) => {
    let tableProps = {
        headsTitles: ['Id', 'Nazwa', 'Opis'],
        modify: true,
        body: TABLES
    };

    let tabsProps = {
        id: "tables-tab",
        tabs: [
            {
                title: 'Edycja stolików',
                content: <CustomTable {...Object.assign({}, tableProps, passed)}/>
            }, {
                title: 'Dodawanie stolików',
                content: <NewTable addingEl={true}/>
            }
        ],
    };

    return (
        <Grid>
            <CustomTabs {...tabsProps}/>
        </Grid>);
};

export default Tables;