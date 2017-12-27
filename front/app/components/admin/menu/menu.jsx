import React from 'react';
import CustomTable from '../../_custom/table';
import MenuData from '../../../_consts/_menu';
import CustomTabs from '../../_custom/tabs';
import {Grid} from 'react-bootstrap';
import NewDish from './newDish';

const Menu = () => {
    let menu = MenuData.map(element => <CustomTable {...element}/>);

    let tabsProps = {
        id: "menu-tab",
        tabs: [
            {
                title: 'Edycja dań',
                content: menu
            }, {
                title: 'Dodawanie dań',
                content: NewDish
            }
        ],
    };

    return (
        <Grid>
            <CustomTabs {...tabsProps}/>
        </Grid>);
};

export default Menu;