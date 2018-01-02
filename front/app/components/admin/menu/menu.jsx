import React from 'react';
import CustomTable from '../../_custom/table';
import MenuData from '../../../_consts/mocks/menu';
import CustomTabs from '../../_custom/tabs';
import {Grid} from 'react-bootstrap';
import NewDish from './dishForm';

const Menu = ({passed}) => {
    console.log('menu:',passed)
    let menu = MenuData.map((element, index) => <CustomTable key={index} {...Object.assign({}, element, passed)}/>);

    let tabsProps = {
        id: "menu-tab",
        tabs: [
            {
                title: 'Edycja dań',
                content: menu
            }, {
                title: 'Dodawanie dań',
                content: <NewDish addingNew=""/>
            }
        ],
    };

    return (
        <Grid>
            <CustomTabs {...tabsProps}/>
        </Grid>);
};

export default Menu;