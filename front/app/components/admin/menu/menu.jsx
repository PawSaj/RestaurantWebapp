import React from 'react';
import CustomTable from '../../_custom/table';
import MenuData from '../../../_consts/mocks/menu';
import CustomTabs from '../../_custom/tabs';
import {Grid} from 'react-bootstrap';
import NewDish from './dishForm';

const Menu = (props) => {
    let menu = MenuData.map((element, index) => <CustomTable key={index} {...Object.assign({}, element, props)}/>);

    let tabsProps = {
        id: "menu-tab",
        tabs: [
            {
                title: 'Edycja dań',
                content: menu
            }, {
                title: 'Dodawanie dań',
                content: <NewDish/>
            }
        ],
    };

    return (
        <Grid>
            <CustomTabs {...tabsProps}/>
        </Grid>);
};

export default Menu;