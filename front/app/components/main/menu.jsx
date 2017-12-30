import React from 'react';
import CustomTable from '../_custom/table';
import MenuData from '../../_consts/mocks/menu';
import {Grid} from 'react-bootstrap';

const Menu = (props) => {
    let menu = MenuData.map((element, index) => {
        element.modify = false;
        return <CustomTable key={index} {...Object.assign({}, element, props)}/>
    });

    return <Grid id="menu-tab">{menu}</Grid>

};

export default Menu;